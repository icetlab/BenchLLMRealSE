"""
LOC Modified (Original -> Reference) for the SCAT pilot's 101-task baseline-
validated working set, computed the same way as PerfOpt's developer-patch
LOC Modified in Analysis/RQ2/patch_complexity.py: lines added + lines
removed in a unified diff, excluding the +++/--- file-header lines.

Used to add a "LOC Modified" column group to Table tab:scat-baseline,
echoing Table tab:perfopt's (Project | # Tasks | LOC Modified | Speedup)
layout.
"""
import difflib
import json
import os

import numpy as np

BASE = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
FINAL120_PATH = os.path.join(BASE, "effibenchx_final120.json")
EXCLUDED_PATH = os.path.join(BASE, "excluded_tasks_reversed_baseline.json")

DIFFICULTY_ORDER = ["Easy", "Medium", "Hard"]


def loc_modified(original: str, reference: str) -> int:
    diff_lines = list(difflib.unified_diff(original.splitlines(), reference.splitlines(), lineterm=""))
    added = sum(1 for l in diff_lines if l.startswith("+") and not l.startswith("+++"))
    removed = sum(1 for l in diff_lines if l.startswith("-") and not l.startswith("---"))
    return added + removed


def main():
    with open(FINAL120_PATH) as f:
        tasks = json.load(f)
    with open(EXCLUDED_PATH) as f:
        excluded = set(json.load(f)["excluded_ids"])

    working_set = [t for t in tasks if t["id"] not in excluded]
    print(f"Working set: {len(working_set)} tasks (excluded {len(excluded)})")

    by_difficulty = {d: [] for d in DIFFICULTY_ORDER}
    for t in working_set:
        lm = loc_modified(t["java_original_solution"], t["java_reference_solution"])
        by_difficulty[t["difficulty"]].append(lm)

    all_values = []
    for d in DIFFICULTY_ORDER:
        vals = by_difficulty[d]
        all_values.extend(vals)
        print(f"  {d:<8} n={len(vals):<4} min={min(vals)} max={max(vals)} avg={np.mean(vals):.2f}")
    print(f"  {'Total':<8} n={len(all_values):<4} min={min(all_values)} max={max(all_values)} avg={np.mean(all_values):.2f}")


if __name__ == "__main__":
    main()
