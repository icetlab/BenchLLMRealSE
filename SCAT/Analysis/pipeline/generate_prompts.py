"""
Generates one No-Hint-style prompt per task (60 total), mirroring the structure
of the real PerfOpt prompt1 ("source files" + "unit tests"), adapted for these
single-file EffiBench-X tasks:
    - "source file"   -> the Original (slow, real accepted submission) Solution.java
    - "unit tests"    -> a handful of representative example test cases (input ->
                         expected output), since there is no real JUnit test file
                         here, only the dataset's generated_tests input/output pairs.

Per the earlier design discussion, this SCAT comparison uses a SINGLE prompting
condition (No-Hint equivalent only) -- no Benchmark or Problem-description tiers,
because (a) our JMH wrapper is generic boilerplate with no extra diagnostic
signal, and (b) there is no real "performance issue description" for these
puzzle tasks the way there is for PerfOpt's real commit-derived tasks.

Output: prompts/{task_id}/prompt.txt (one file per task).
"""
import json
import os

BASE = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
OUT_DIR = os.path.join(BASE, "prompts")
MAX_EXAMPLE_TESTS = 5
MAX_EXAMPLE_INPUT_LEN = 400  # skip pathologically large test cases when picking examples for the prompt


def pick_example_tests(tests, n=MAX_EXAMPLE_TESTS):
    short_tests = [t for t in tests if len(t["input"]) <= MAX_EXAMPLE_INPUT_LEN]
    pool = short_tests if len(short_tests) >= n else tests
    return pool[:n]


def format_tests(tests):
    lines = []
    for i, t in enumerate(tests):
        lines.append(f"Example {i + 1}:\n  input: {t['input']}\n  expected output: {t['output']}")
    return "\n".join(lines)


def build_prompt(task):
    source = task["java_original_solution"]
    tests = json.loads(task["generated_tests"]) if isinstance(task["generated_tests"], str) else task["generated_tests"]
    examples = pick_example_tests(tests)
    tests_block = format_tests(examples)

    prompt = (
        f">> The source file (Solution.java) is:\n{source}\n"
        f"---------\n"
        f">> Example test cases are:\n{tests_block}\n"
    )
    return prompt


def main():
    with open(os.path.join(BASE, "effibenchx_final60.json")) as f:
        tasks = json.load(f)

    os.makedirs(OUT_DIR, exist_ok=True)
    for t in tasks:
        tid = t["id"]
        task_dir = os.path.join(OUT_DIR, tid)
        os.makedirs(task_dir, exist_ok=True)
        prompt = build_prompt(t)
        with open(os.path.join(task_dir, "prompt.txt"), "w", encoding="utf-8") as f:
            f.write(prompt)

    print(f"Generated {len(tasks)} prompts under {OUT_DIR}/{{task_id}}/prompt.txt")


if __name__ == "__main__":
    main()
