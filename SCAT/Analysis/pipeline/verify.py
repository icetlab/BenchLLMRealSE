"""
Correctness verification for a candidate Solution.java against one task's real
generated_tests, reusing that task's OFFICIAL EffiBench-X evaluator_py function
(loaded and exec'd directly from the dataset) rather than reimplementing
comparison logic ourselves.

Usage:
    from verify import verify_solution
    ok, details = verify_solution(task, "/path/to/candidate/Solution.java")
"""
import json
import os
import shutil
import subprocess
import tempfile

BASE = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
JMH_PROJECT = os.path.join(BASE, "jmh_project")
JAVA_SRC_ROOT = os.path.join(JMH_PROJECT, "src", "main", "java", "effibenchjmh")
CLASSES_ROOT = os.path.join(JMH_PROJECT, "target", "classes")
COMMON_CLASSES = CLASSES_ROOT  # TestUtil.class / Pair.class already live under effibenchjmh/common here

MARKER = "###RESULT###"
JAVAC_TIMEOUT = 60
RUN_TIMEOUT = 15


def _load_evaluator(task):
    ns = {}
    # Some evaluators use PEP 604 "X | None" return-type hints, valid syntax only
    # on Python 3.10+; `from __future__ import annotations` (PEP 563) defers
    # annotation evaluation to strings so `def ... -> int | None:` doesn't crash
    # at def-time on 3.9. Harmless for evaluators that don't use this syntax.
    # (Checked: none of the original 60 tasks' evaluators hit this -- see
    # effibenchx_final60.json -- but the new60 batch does, e.g. id 3500.)
    exec("from __future__ import annotations\n" + task["evaluator_py"], ns)
    return ns["evaluate"]


def compile_candidate(task_id, solution_source, workdir):
    """
    Compiles a candidate Solution.java (arbitrary source text) together with a
    copy of that task's Runner.java, in an isolated temp package, linked against
    the already-built effibenchjmh.common classes (TestUtil, Pair).

    Returns (ok: bool, message: str, classes_dir: str | None, package: str | None).
    """
    pkg = f"cand_{task_id}"
    pkg_dir = os.path.join(workdir, "src", "effibenchjmh", pkg)
    os.makedirs(pkg_dir, exist_ok=True)

    # Adapt the candidate solution + the task's existing Runner.java (reference
    # variant) into this isolated package.
    sol_path = os.path.join(pkg_dir, "Solution.java")
    with open(sol_path, "w", encoding="utf-8") as f:
        # Only import our javafx.util.Pair stand-in if the candidate doesn't
        # already define its own "class Pair" (some real submissions do, e.g.
        # id=3562) -- importing on top of a same-named local class is a compile
        # error ("Pair is already defined in this compilation unit").
        pair_import = "" if "class Pair" in solution_source else "import effibenchjmh.common.Pair;\n"
        # Broadened beyond java.util/java.util.stream after id=3363's Original
        # solution (a real LeetCode submission) used ConcurrentSkipListMap with no
        # explicit import of its own -- LeetCode's own judge apparently has a wider
        # default import set than the bare minimum we started with.
        header = (
            f"package effibenchjmh.{pkg};\n\n"
            "import java.util.*;\n"
            "import java.util.stream.*;\n"
            "import java.util.concurrent.*;\n"
            "import java.util.concurrent.atomic.*;\n"
            "import java.math.*;\n"
            f"{pair_import}\n"
        )
        body = solution_source
        # Strip any pre-existing package declaration from the candidate source.
        lines = body.splitlines()
        if lines and lines[0].strip().startswith("package "):
            body = "\n".join(lines[1:])
        f.write(header + body + "\n")

    runner_src_path = os.path.join(JAVA_SRC_ROOT, f"task{task_id}", "Runner.java")
    with open(runner_src_path, encoding="utf-8") as f:
        runner_code = f.read()
    runner_code = runner_code.replace(f"package effibenchjmh.task{task_id};", f"package effibenchjmh.{pkg};")
    with open(os.path.join(pkg_dir, "Runner.java"), "w", encoding="utf-8") as f:
        f.write(runner_code)

    classes_out = os.path.join(workdir, "classes")
    os.makedirs(classes_out, exist_ok=True)

    cmd = [
        "javac", "-encoding", "UTF-8",
        "-cp", COMMON_CLASSES,
        "-d", classes_out,
        sol_path, os.path.join(pkg_dir, "Runner.java"),
    ]
    try:
        proc = subprocess.run(cmd, capture_output=True, text=True, timeout=JAVAC_TIMEOUT)
    except subprocess.TimeoutExpired:
        return False, "[COMPILE TIMEOUT]", None, None

    if proc.returncode != 0:
        return False, f"[COMPILE FAILED]\n{proc.stdout}\n{proc.stderr}", None, None
    return True, "[COMPILE OK]", classes_out, pkg


def run_one_case(classes_dir, package, input_str):
    cp = f"{classes_dir}:{COMMON_CLASSES}"
    cmd = ["java", "-cp", cp, f"effibenchjmh.{package}.Runner"]
    try:
        proc = subprocess.run(cmd, input=input_str, capture_output=True, text=True, timeout=RUN_TIMEOUT)
    except subprocess.TimeoutExpired:
        return None, "[RUNTIME TIMEOUT]"
    if MARKER not in proc.stdout:
        return None, f"[RUNTIME ERROR / NO OUTPUT]\n{proc.stdout}\n{proc.stderr}"
    result = proc.stdout.split(MARKER, 1)[1].strip("\n")
    return result, proc.stderr


def verify_solution(task, solution_source, max_cases=None):
    """
    task: one entry from effibenchx_final60.json (dict with generated_tests, evaluator_py, id)
    solution_source: full Java source text of the candidate Solution.java
    max_cases: optionally cap how many test cases to check (None = all)

    Returns (plausible: bool, detail: dict) where detail has keys:
        stage: "compile" | "test" | "pass"
        message: human-readable diagnostic (compiler errors, first failing case, etc.)
        passed / total: test case counts (only present if stage == "test" or "pass")
    """
    tid = task["id"]
    tests = json.loads(task["generated_tests"]) if isinstance(task["generated_tests"], str) else task["generated_tests"]
    if max_cases:
        tests = tests[:max_cases]
    evaluate = _load_evaluator(task)

    with tempfile.TemporaryDirectory(prefix=f"verify_{tid}_") as workdir:
        ok, msg, classes_dir, package = compile_candidate(tid, solution_source, workdir)
        if not ok:
            return False, {"stage": "compile", "message": msg}

        passed = 0
        first_failure = None
        for i, case in enumerate(tests):
            out, err = run_one_case(classes_dir, package, case["input"])
            if out is None:
                if first_failure is None:
                    first_failure = f"case {i}: {err[:500]}"
                continue
            try:
                ok_case = evaluate(case["output"], out)
            except Exception as e:
                ok_case = False
                if first_failure is None:
                    first_failure = f"case {i}: evaluator raised {e}"
            if ok_case:
                passed += 1
            elif first_failure is None:
                first_failure = f"case {i}: expected={case['output']!r} got={out!r}"

        total = len(tests)
        plausible = (passed == total)
        stage = "pass" if plausible else "test"
        message = "[ALL TESTS PASSED]" if plausible else f"[TEST FAILED] {first_failure}"
        return plausible, {"stage": stage, "message": message, "passed": passed, "total": total}


if __name__ == "__main__":
    # Quick self-check against the Original solutions themselves (should all pass).
    with open(os.path.join(BASE, "effibenchx_final60.json")) as f:
        all_tasks = json.load(f)

    import sys
    sample = all_tasks if len(sys.argv) < 2 else [t for t in all_tasks if t["id"] in sys.argv[1:]]
    for t in sample:
        ok, detail = verify_solution(t, t["java_original_solution"], max_cases=5)
        print(f"id={t['id']:<6} original: {'PASS' if ok else 'FAIL'} ({detail.get('passed','?')}/{detail.get('total','?')}) {detail['message'][:120]}")
