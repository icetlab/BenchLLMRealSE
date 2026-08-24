"""
Infers a parsing "recipe" (matching recipes.py's convention) for each of the 60
new EffiBench-X tasks, by trying candidate (delimiter, per-param-accessor)
combinations and validating each candidate end-to-end: compile the task's own
Reference solution (a real, already-accepted human submission -- known correct)
together with a Runner.java built from the candidate recipe, run it against
several of the task's generated_tests, and check the output against the task's
own evaluator_py. A candidate is accepted only if it passes every sampled case.

This is the same self-validating trick verify.py uses for LLM patches, applied
here to validate the *parsing recipe itself* rather than a patch: since
Reference is guaranteed functionally correct, any output mismatch can only be
a parsing/serialization bug in the candidate recipe, not a bug in the solution.

Output:
  - recipes_new60.json: {task_id: {"parse": [...], "args": [...]}} for every
    task that validated successfully
  - serialization_new60.json: {task_id: "<serialization tag>"} for non-scalar
    return types
  - Prints a manual-review list for any task where no candidate validated.
"""
import json
import os
import re
import subprocess
import sys
import tempfile

BASE = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
JMH_PROJECT = os.path.join(BASE, "jmh_project")
CLASSES_ROOT = os.path.join(JMH_PROJECT, "target", "classes")
NEW60_PATH = os.path.join(BASE, "effibenchx_new60.json")

MARKER = "###RESULT###"
JAVAC_TIMEOUT = 60
RUN_TIMEOUT = 15
N_VALIDATION_CASES = 4  # how many generated_tests to check per candidate


def method_sig(starter_code):
    m = re.search(r'public\s+([\w\[\]<>,\s]+?)\s+(\w+)\s*\(([^)]*)\)', starter_code)
    if not m:
        return None
    rtype, mname, params_raw = m.group(1).strip(), m.group(2), m.group(3)
    ptypes = []
    if params_raw.strip():
        depth = 0
        buf = ""
        parts = []
        for ch in params_raw:
            if ch == "<":
                depth += 1
            elif ch == ">":
                depth -= 1
            if ch == "," and depth == 0:
                parts.append(buf)
                buf = ""
            else:
                buf += ch
        parts.append(buf)
        for p in parts:
            p = p.strip()
            ptype = " ".join(p.split(" ")[:-1])
            ptypes.append(ptype)
    return rtype, mname, ptypes


def scalar_accessor(ptype, token_expr):
    """Java expression to turn a raw token string into a value of type `ptype`."""
    return {
        "int": f"Integer.parseInt({token_expr})",
        "long": f"Long.parseLong({token_expr})",
        "double": f"Double.parseDouble({token_expr})",
        "boolean": f"Boolean.parseBoolean({token_expr})",
        "char": f"{token_expr}.trim().charAt(0)",
        "String": token_expr,
        "int[]": f"TestUtil.parseIntArray({token_expr})",
        "long[]": f"TestUtil.parseIntArray({token_expr})",  # widens; rare
        "int[][]": f"TestUtil.parseIntMatrixSemi({token_expr})",
        "char[][]": f"TestUtil.parseCharMatrixSemi({token_expr})",
        "String[]": f"TestUtil.parseStringArray({token_expr})",
        "List<Integer>": f"TestUtil.parseIntList({token_expr})",
        "List<List<Integer>>": f"TestUtil.parseIntListMatrix({token_expr})",
        "List<String>": f"java.util.Arrays.asList(TestUtil.parseStringArray({token_expr}))",
    }.get(ptype)


DELIMS = [(" ", ' ', "String[] tok = line.split(\" \", -1);"),
          (",", ',', "String[] tok = line.split(\",\", -1);"),
          ("\n", "\\n", "String[] tok = line.split(\"\\n\", -1);"),
          ("|", "\\\\|", "String[] tok = line.split(\"\\\\|\", -1);")]


def candidate_recipes(ptypes):
    """Yield candidate (parse_lines, args) recipes to try, in priority order."""
    if len(ptypes) == 0:
        return
    if len(ptypes) == 1:
        acc = scalar_accessor(ptypes[0], "line")
        if acc:
            yield ([], [acc])
        return
    for _, _, split_stmt in DELIMS:
        args = []
        ok = True
        for i, pt in enumerate(ptypes):
            acc = scalar_accessor(pt, f"tok[{i}]")
            if acc is None:
                ok = False
                break
            args.append(acc)
        if ok:
            yield ([split_stmt], args)


def serialization_candidates(rtype):
    """Yield candidate serialization tags to try, in priority order (None = scalar first)."""
    if rtype in ("int", "long", "boolean", "String", "double", "char"):
        yield None
        return
    if rtype == "int[]":
        yield "int[]:comma"
        yield "int[]:newline"
        return
    if rtype == "long[]":
        yield "long[]:comma"
        return
    if rtype == "List<Integer>":
        yield "List<Integer>:comma"
        return
    if rtype == "List<String>":
        yield "List<String>:comma"
        return
    return


def print_statement(ser):
    if ser == "int[]:comma":
        return 'System.out.println(java.util.stream.IntStream.of(result).mapToObj(String::valueOf).collect(java.util.stream.Collectors.joining(",")));'
    if ser == "int[]:newline":
        return 'System.out.println(java.util.stream.IntStream.of(result).mapToObj(String::valueOf).collect(java.util.stream.Collectors.joining("\\n")));'
    if ser == "long[]:comma":
        return 'System.out.println(java.util.stream.LongStream.of(result).mapToObj(String::valueOf).collect(java.util.stream.Collectors.joining(",")));'
    if ser == "List<Integer>:comma":
        return 'System.out.println(result.stream().map(String::valueOf).collect(java.util.stream.Collectors.joining(",")));'
    if ser == "List<String>:comma":
        return 'System.out.println(String.join(",", result));'
    return 'System.out.println(result);'


RUNNER_TEMPLATE = '''package effibenchjmh.{pkg};

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

public class Runner {{
    public static void main(String[] args) throws Exception {{
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);
        Solution sol = new Solution();
{parse_block}
        var result = sol.{method}({args_joined});
        System.out.println("###RESULT###");
        {print_stmt}
    }}
}}
'''


def build_and_test_candidate(task, method, parse_lines, args, ser, n_cases):
    pkg = f"cand_{task['id']}"
    with tempfile.TemporaryDirectory(prefix=f"inferrecipe_{task['id']}_") as workdir:
        pkg_dir = os.path.join(workdir, "src", "effibenchjmh", pkg)
        os.makedirs(pkg_dir, exist_ok=True)

        sol_src = task["java_reference_solution"]
        pair_import = "" if "class Pair" in sol_src else "import effibenchjmh.common.Pair;\n"
        header = f"package effibenchjmh.{pkg};\n\nimport java.util.*;\nimport java.util.stream.*;\n{pair_import}\n"
        body = sol_src
        lines = body.splitlines()
        if lines and lines[0].strip().startswith("package "):
            body = "\n".join(lines[1:])
        with open(os.path.join(pkg_dir, "Solution.java"), "w") as f:
            f.write(header + body + "\n")

        parse_block = "\n".join("        " + l for l in parse_lines)
        runner_code = RUNNER_TEMPLATE.format(
            pkg=pkg, method=method, parse_block=parse_block,
            args_joined=", ".join(args), print_stmt=print_statement(ser),
        )
        with open(os.path.join(pkg_dir, "Runner.java"), "w") as f:
            f.write(runner_code)

        classes_out = os.path.join(workdir, "classes")
        os.makedirs(classes_out, exist_ok=True)
        cmd = ["javac", "-encoding", "UTF-8", "-cp", CLASSES_ROOT, "-d", classes_out,
               os.path.join(pkg_dir, "Solution.java"), os.path.join(pkg_dir, "Runner.java")]
        try:
            proc = subprocess.run(cmd, capture_output=True, text=True, timeout=JAVAC_TIMEOUT)
        except subprocess.TimeoutExpired:
            return False, "compile timeout"
        if proc.returncode != 0:
            return False, f"compile failed: {proc.stderr[:300]}"

        tests = json.loads(task["generated_tests"]) if isinstance(task["generated_tests"], str) else task["generated_tests"]
        ns = {}
        # Some evaluators use PEP 604 "X | None" return-type hints, which are only
        # valid syntax at runtime on Python 3.10+. `from __future__ import
        # annotations` defers annotation evaluation to strings (PEP 563), so the
        # def statement no longer tries to evaluate `int | None` eagerly. Harmless
        # for evaluators that don't use this syntax.
        exec("from __future__ import annotations\n" + task["evaluator_py"], ns)
        evaluate = ns["evaluate"]

        cp = f"{classes_out}:{CLASSES_ROOT}"
        for case in tests[:n_cases]:
            try:
                proc = subprocess.run(["java", "-cp", cp, f"effibenchjmh.{pkg}.Runner"],
                                       input=case["input"], capture_output=True, text=True, timeout=RUN_TIMEOUT)
            except subprocess.TimeoutExpired:
                return False, "runtime timeout"
            if MARKER not in proc.stdout:
                return False, f"no marker in output: {proc.stdout[:200]} {proc.stderr[:200]}"
            out = proc.stdout.split(MARKER, 1)[1].strip("\n")
            try:
                ok = evaluate(case["output"], out)
            except Exception as e:
                return False, f"evaluator raised {e}"
            if not ok:
                return False, f"mismatch: expected={case['output']!r} got={out!r}"
        return True, "ok"


def main():
    with open(NEW60_PATH) as f:
        tasks = json.load(f)

    recipes_out = {}
    serialization_out = {}
    manual_review = []

    for i, task in enumerate(tasks):
        tid = task["id"]
        sig = method_sig(task["java_starter_code"])
        if not sig:
            manual_review.append((tid, task["title"], "could not parse method signature"))
            continue
        rtype, method, ptypes = sig

        found = False
        tried = []
        for parse_lines, args in candidate_recipes(ptypes):
            for ser in serialization_candidates(rtype):
                ok, msg = build_and_test_candidate(task, method, parse_lines, args, ser, N_VALIDATION_CASES)
                tried.append((parse_lines, args, ser, msg))
                if ok:
                    recipes_out[tid] = {"parse": parse_lines, "args": args}
                    if ser:
                        serialization_out[tid] = ser
                    found = True
                    break
            if found:
                break

        status = "OK" if found else "MANUAL REVIEW"
        print(f"[{i+1}/{len(tasks)}] {tid} {task['title'][:40]:<40} rtype={rtype:<15} nparams={len(ptypes)} -> {status}")
        if not found:
            manual_review.append((tid, task["title"], tried[-1][3] if tried else "no candidates generated"))

    print(f"\n=== Summary: {len(recipes_out)}/{len(tasks)} auto-validated, {len(manual_review)} need manual review ===")
    for tid, title, reason in manual_review:
        print(f"  {tid}  {title[:50]:<50} last error: {reason[:150]}")

    with open(os.path.join(BASE, "recipes_new60.json"), "w") as f:
        json.dump(recipes_out, f, indent=2)
    with open(os.path.join(BASE, "serialization_new60.json"), "w") as f:
        json.dump(serialization_out, f, indent=2)
    with open(os.path.join(BASE, "manual_review_new60.json"), "w") as f:
        json.dump(manual_review, f, indent=2)
    print(f"\nSaved recipes_new60.json, serialization_new60.json, manual_review_new60.json")


if __name__ == "__main__":
    main()
