"""
Draws a SECOND stratified sample of 60 Java LeetCode tasks from EffiBench-X,
disjoint from the original 60 (scat_papers/effibenchx_final60.json), to double
the SCAT pilot's sample size per the advisor's request (60 -> 120 total).

Mirrors the original sampling logic:
  - source == "leetcode", has a Java solution and a difficulty label
  - Reference = the canonical solution in solutions.java.code
  - Original = the slowest OTHER accepted submission in
    solutions.java.runtimeDistribution (excluding the entry matching the
    canonical code itself)
  - Require a minimum 1.2x measured runtime gap (Original/Reference) based on
    EffiBench-X's own recorded timings; tasks that don't meet this, or where
    the reference's own recorded runtime is 0 (undefined ratio), are rejected
    and replaced by drawing another candidate from the same difficulty stratum
  - Stratified 20/20/20 across Easy/Medium/Hard

Output: scat_papers/effibenchx_new60.json, in the same schema as
effibenchx_final60.json (so it can be concatenated directly).
"""
import json
import os
import random

from datasets import load_dataset

BASE = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
EXISTING_60_PATH = os.path.join(BASE, "effibenchx_final60.json")
OUT_PATH = os.path.join(BASE, "effibenchx_new60.json")

MIN_RATIO = 1.2
PER_STRATUM = 20
SEED = 4242


def load_existing_ids():
    with open(EXISTING_60_PATH) as f:
        data = json.load(f)
    return {t["id"] for t in data}


def pick_reference_and_original(java_sol):
    canonical_code = java_sol.get("code")
    if not canonical_code:
        return None
    # Reference's own measured runtime is the top-level `runtime` field, NOT
    # found by string-matching the canonical code inside runtimeDistribution
    # (verified against the existing 60-task set: original_vs_reference_ratio
    # == slowest_runtimeDistribution_entry / top-level `runtime`, exactly).
    ref_runtime = java_sol.get("runtime")
    if ref_runtime is None or ref_runtime == 0:
        return None

    rd_raw = java_sol.get("runtimeDistribution")
    if not rd_raw:
        return None
    try:
        rd = json.loads(rd_raw)
    except (json.JSONDecodeError, TypeError):
        return None

    candidates = []
    for entry in rd:
        if len(entry) < 3:
            continue
        runtime, _, code = entry[0], entry[1], entry[2]
        if not code:
            continue
        # Defensively exclude any entry identical to the canonical solution
        # itself, so Original is never accidentally the same code as Reference.
        if code.strip() == canonical_code.strip():
            continue
        candidates.append((runtime, code))

    if not candidates:
        return None

    # Original = slowest other accepted submission.
    orig_runtime, orig_code = max(candidates, key=lambda c: c[0])
    ratio = orig_runtime / ref_runtime
    if ratio < MIN_RATIO:
        return None

    return {
        "java_reference_solution": canonical_code,
        "java_original_solution": orig_code,
        "original_vs_reference_ratio": ratio,
    }


def build_task_record(row, ref_orig):
    return {
        "id": str(row["id"]),
        "title": row["title"],
        "title_slug": row["title_slug"],
        "source": row["source"],
        "difficulty": row["difficulty"],
        "url": row["url"],
        "description": row["description"],
        "time_limit_nanos": row["time_limit_nanos"],
        "memory_limit_bytes": row["memory_limit_bytes"],
        "java_starter_code": row["starter_code"]["java"] if row.get("starter_code") else None,
        "java_reference_solution": ref_orig["java_reference_solution"],
        "test_case_generator_py": row["test_case_generator"],
        "evaluator_py": row["evaluator"],
        "generated_tests": row["generated_tests"],
        "original_vs_reference_ratio": ref_orig["original_vs_reference_ratio"],
        "java_original_solution": ref_orig["java_original_solution"],
    }


def main():
    existing_ids = load_existing_ids()
    print(f"Excluding {len(existing_ids)} already-sampled task IDs")

    ds = load_dataset("EffiBench/effibench-x", split="test")
    pool = [
        r for r in ds
        if r.get("source") == "leetcode"
        and r.get("difficulty")
        and r.get("solutions", {}).get("java")
        and str(r["id"]) not in existing_ids
    ]
    print(f"Eligible pool (leetcode, java, difficulty, not already used): {len(pool)}")

    by_diff = {"Easy": [], "Medium": [], "Hard": []}
    for r in pool:
        d = r["difficulty"]
        if d in by_diff:
            by_diff[d].append(r)

    rng = random.Random(SEED)
    for d in by_diff:
        rng.shuffle(by_diff[d])

    selected = []
    attempts_log = {"Easy": 0, "Medium": 0, "Hard": 0}
    rejected_log = {"Easy": 0, "Medium": 0, "Hard": 0}

    for diff in ["Easy", "Medium", "Hard"]:
        chosen = 0
        idx = 0
        while chosen < PER_STRATUM and idx < len(by_diff[diff]):
            row = by_diff[diff][idx]
            idx += 1
            attempts_log[diff] += 1
            ref_orig = pick_reference_and_original(row["solutions"]["java"])
            if ref_orig is None:
                rejected_log[diff] += 1
                continue
            selected.append(build_task_record(row, ref_orig))
            chosen += 1
        if chosen < PER_STRATUM:
            print(f"  [warn] only found {chosen}/{PER_STRATUM} valid {diff} tasks in the pool")

    print("\n=== Sampling summary ===")
    for diff in ["Easy", "Medium", "Hard"]:
        kept = sum(1 for t in selected if t["difficulty"] == diff)
        print(f"  {diff}: {kept} kept / {attempts_log[diff]} tried ({rejected_log[diff]} rejected on threshold/data quality)")
    print(f"  Total: {len(selected)}")

    with open(OUT_PATH, "w") as f:
        json.dump(selected, f, indent=2)
    print(f"\nSaved {len(selected)} new tasks to {OUT_PATH}")


if __name__ == "__main__":
    main()
