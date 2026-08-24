"""
Orchestrates the full generate -> apply -> compile -> test -> repair loop for
all 60 tasks x N models x M trials, matching the paper's stated protocol:
    - up to 2 repair attempts for integration/format failures (invalid patch format,
      or a search block that can't be applied)
    - up to 3 repair attempts for verification failures (compile errors or failing
      the provided test cases)
    - a solution is "plausible" only if it produces a validly-formatted patch that
      applies cleanly, compiles, AND passes every one of the task's generated_tests

Usage:
    python run_pipeline.py --models o4-mini gemini --trials 3
    python run_pipeline.py --models deepseek-v3 --tasks 3199 3350 --trials 1

Results are written to results/{task_id}/{model}/trial{n}/:
    status.json          -- final outcome + iteration counts
    iter{k}_prompt.txt    -- the prompt sent for repair attempt k (0 = original)
    iter{k}_llm_output.txt
    iter{k}_diagnostic.txt (repair feedback given to the model, if any)
    Solution.java         -- the final patched source (only if plausible)
"""
import argparse
import json
import os
import sys
import traceback

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from call_llms import call_llm
from apply_patch import apply_patch, PatchFormatError
from verify import verify_solution

BASE = os.path.dirname(os.path.abspath(__file__))
PROJECT_ROOT = os.path.dirname(BASE)
PROMPTS_DIR = os.path.join(PROJECT_ROOT, "prompts")
RESULTS_DIR = os.path.join(PROJECT_ROOT, "results")

MAX_FORMAT_REPAIRS = 2       # matches the paper's protocol for integration/format failures
MAX_VERIFY_REPAIRS = 3       # matches the paper's protocol for compile/test failures


def load_tasks(task_filter=None, task_file="effibenchx_final60.json"):
    with open(os.path.join(PROJECT_ROOT, task_file)) as f:
        tasks = json.load(f)
    if task_filter:
        tasks = [t for t in tasks if t["id"] in task_filter]
    return tasks


def get_prompt(task_id):
    with open(os.path.join(PROMPTS_DIR, task_id, "prompt.txt")) as f:
        return f.read()


def run_one_trial(task, model, trial_idx, out_dir):
    os.makedirs(out_dir, exist_ok=True)
    original_prompt = get_prompt(task["id"])
    current_prompt = original_prompt
    llm_output = ""
    iteration = 0
    format_attempts = 0
    verify_attempts = 0

    while True:
        with open(os.path.join(out_dir, f"iter{iteration}_prompt.txt"), "w") as f:
            f.write(current_prompt)

        try:
            llm_output = call_llm(model, current_prompt)
        except Exception as e:
            status = {"outcome": "error", "stage": "llm_call", "message": str(e), "iteration": iteration}
            _save_status(out_dir, status)
            return status

        with open(os.path.join(out_dir, f"iter{iteration}_llm_output.txt"), "w") as f:
            f.write(llm_output)

        # Stage 1: parse + apply the patch (format/integration check)
        try:
            patched_source = apply_patch(task["java_original_solution"], llm_output)
        except PatchFormatError as e:
            format_attempts += 1
            if format_attempts > MAX_FORMAT_REPAIRS:
                status = {
                    "outcome": "implausible", "stage": "format", "message": str(e),
                    "iteration": iteration, "format_attempts": format_attempts - 1,
                    "verify_attempts": verify_attempts,
                }
                _save_status(out_dir, status)
                return status
            diagnostic = (
                f">>>> The format of your generated code changes does not meet the requirements, "
                f"with the following error:\n{e}\n"
                f">>>> Your previous output was:\n{llm_output}\n"
                f">>>> Analyze the error. If the search block was not found, try to provide a more "
                f"flexible search pattern or break the change into smaller chunks. Make sure to "
                f"maintain the same JSON format with filepath, search, and replace fields.\n"
                f">>>> Original prompt was:\n{original_prompt}\n"
            )
            with open(os.path.join(out_dir, f"iter{iteration}_diagnostic.txt"), "w") as f:
                f.write(diagnostic)
            current_prompt = diagnostic
            iteration += 1
            continue

        # Stage 2: compile + run the provided tests (verification check)
        plausible, detail = verify_solution(task, patched_source)
        if plausible:
            with open(os.path.join(out_dir, "Solution.java"), "w") as f:
                f.write(patched_source)
            status = {
                "outcome": "plausible", "iteration": iteration,
                "format_attempts": format_attempts, "verify_attempts": verify_attempts,
                "passed": detail["passed"], "total": detail["total"],
            }
            _save_status(out_dir, status)
            return status

        verify_attempts += 1
        if verify_attempts > MAX_VERIFY_REPAIRS:
            status = {
                "outcome": "implausible", "stage": detail["stage"], "message": detail["message"],
                "iteration": iteration, "format_attempts": format_attempts,
                "verify_attempts": verify_attempts - 1,
                "passed": detail.get("passed"), "total": detail.get("total"),
            }
            _save_status(out_dir, status)
            return status

        diagnostic = (
            f">>>> Your previous patch did not pass verification, with the following feedback:\n"
            f"{detail['message']}\n"
            f">>>> Your previous output was:\n{llm_output}\n"
            f">>>> Analyze the error and produce a corrected patch. Maintain the same JSON format "
            f"with filepath, search, and replace fields.\n"
            f">>>> Original prompt was:\n{original_prompt}\n"
        )
        with open(os.path.join(out_dir, f"iter{iteration}_diagnostic.txt"), "w") as f:
            f.write(diagnostic)
        current_prompt = diagnostic
        iteration += 1


def _save_status(out_dir, status):
    with open(os.path.join(out_dir, "status.json"), "w") as f:
        json.dump(status, f, indent=2)


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--models", nargs="+", required=True, choices=["o4-mini", "gemini", "deepseek-v3", "deepseek-r1"])
    ap.add_argument("--trials", type=int, default=3)
    ap.add_argument("--tasks", nargs="+", default=None, help="Optional subset of task IDs; default: all 60")
    ap.add_argument("--task-file", default="effibenchx_final60.json", help="Task JSON file (relative to scat_papers/), e.g. effibenchx_new60.json")
    args = ap.parse_args()

    tasks = load_tasks(set(args.tasks) if args.tasks else None, task_file=args.task_file)
    print(f"Running {len(tasks)} tasks x {len(args.models)} models x {args.trials} trials")

    for task in tasks:
        for model in args.models:
            for trial in range(1, args.trials + 1):
                out_dir = os.path.join(RESULTS_DIR, task["id"], model, f"trial{trial}")
                status_path = os.path.join(out_dir, "status.json")
                if os.path.exists(status_path):
                    with open(status_path) as f:
                        prior = json.load(f)
                    if prior.get("outcome") == "error":
                        # A crash (API error, bug, etc.) is not a real result -- retry it.
                        print(f"  [retry] {task['id']}/{model}/trial{trial} previously errored ({prior.get('message', '')[:80]})")
                    else:
                        print(f"  [skip] {task['id']}/{model}/trial{trial} already has a result ({prior['outcome']})")
                        continue
                print(f"  [run]  {task['id']}/{model}/trial{trial} ...")
                try:
                    status = run_one_trial(task, model, trial, out_dir)
                    print(f"         -> {status['outcome']}")
                except Exception:
                    print(f"         -> CRASHED:\n{traceback.format_exc()}")


if __name__ == "__main__":
    main()
