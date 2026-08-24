"""
Generates a JMH benchmark package for every plausible LLM-generated patch, so
their performance can be measured on the VM alongside the existing
Original/Reference packages.

For each task {id} and model {model} with results/{id}/{model}/trial1/status.json
outcome == "plausible" (i.e. Solution.java exists and passed correctness
verification), creates:

    jmh_project/src/main/java/effibenchjmh/task{id}/llm_{model}/Solution.java
    jmh_project/src/main/java/effibenchjmh/task{id}/llm_{model}/Bench.java

Solution.java is built the same way verify.py's compile_candidate() already
proved compiles: package decl + common imports (+ Pair import unless the
candidate defines its own) prepended to the candidate body (package line
stripped if present). Since the pipeline's own verify step already compiled
and ran this exact source successfully, this is a mechanical re-package, not
a new correctness risk.

Bench.java is copied verbatim from the task's existing reference package
(task{id}/Bench.java), with only the package declaration rewritten. This is
safe because:
  - the resource path for reading test cases is an ABSOLUTE path
    ("/effibenchjmh/task{id}/caseN.txt"), so it resolves the same regardless
    of which package Bench.java itself lives in
  - a patch only changes the body of the solution method, never its name or
    signature (apply_patch operates on method bodies), so the exact same
    `sol.method(args...)` call in Bench.java remains valid

Runner.java (used only for correctness verification, not performance) is
intentionally NOT generated here -- verification already happened once in
run_pipeline.py, and duplicating it adds nothing for a pure JMH run.
"""
import json
import os
import re

BASE = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
JAVA_ROOT = os.path.join(BASE, "jmh_project", "src", "main", "java", "effibenchjmh")
RESULTS_DIR = os.path.join(BASE, "results")

MODEL_PKG_NAME = {
    "o4-mini": "llm_o4mini",
    "gemini": "llm_gemini",
    "deepseek-v3": "llm_deepseekv3",
    "deepseek-r1": "llm_deepseekr1",
}


def build_solution_java(task_id, pkg, solution_source):
    pair_import = "" if "class Pair" in solution_source else "import effibenchjmh.common.Pair;\n"
    # Broadened to match verify.py / build_jmh_new60.py after id=3363's Original
    # solution needed java.util.concurrent with no explicit import of its own.
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
    lines = body.splitlines()
    if lines and lines[0].strip().startswith("package "):
        body = "\n".join(lines[1:])
    return header + body + "\n"


def build_bench_java(task_id, model, pkg):
    ref_bench_path = os.path.join(JAVA_ROOT, f"task{task_id}", "Bench.java")
    with open(ref_bench_path, encoding="utf-8") as f:
        code = f.read()
    code = re.sub(
        rf"^package effibenchjmh\.task{task_id};$",
        f"package effibenchjmh.{pkg};",
        code, count=1, flags=re.MULTILINE,
    )
    code = code.replace(
        f"/** id={task_id} |",
        f"/** id={task_id} | LLM PATCH by {model} | ",
        1,
    )
    return code


def main():
    generated = 0
    skipped_missing_ref = []
    for task_id in sorted(os.listdir(RESULTS_DIR)):
        task_dir = os.path.join(RESULTS_DIR, task_id)
        if not os.path.isdir(task_dir):
            continue
        ref_bench_path = os.path.join(JAVA_ROOT, f"task{task_id}", "Bench.java")
        if not os.path.isfile(ref_bench_path):
            skipped_missing_ref.append(task_id)
            continue

        for model in sorted(os.listdir(task_dir)):
            trial_dir = os.path.join(task_dir, model, "trial1")
            status_path = os.path.join(trial_dir, "status.json")
            sol_path = os.path.join(trial_dir, "Solution.java")
            if not (os.path.isfile(status_path) and os.path.isfile(sol_path)):
                continue
            with open(status_path) as f:
                status = json.load(f)
            if status.get("outcome") != "plausible":
                continue
            if model not in MODEL_PKG_NAME:
                print(f"  [warn] unknown model dir name '{model}' for task {task_id}, skipping")
                continue

            pkg_suffix = MODEL_PKG_NAME[model]
            pkg = f"task{task_id}.{pkg_suffix}"
            out_dir = os.path.join(JAVA_ROOT, f"task{task_id}", pkg_suffix)
            os.makedirs(out_dir, exist_ok=True)

            with open(sol_path, encoding="utf-8") as f:
                solution_source = f.read()

            with open(os.path.join(out_dir, "Solution.java"), "w", encoding="utf-8") as f:
                f.write(build_solution_java(task_id, pkg, solution_source))
            with open(os.path.join(out_dir, "Bench.java"), "w", encoding="utf-8") as f:
                f.write(build_bench_java(task_id, model, pkg))

            generated += 1

    print(f"Generated {generated} LLM-patch JMH packages")
    if skipped_missing_ref:
        print(f"Skipped {len(skipped_missing_ref)} task dirs with no reference Bench.java: {skipped_missing_ref}")


if __name__ == "__main__":
    main()
