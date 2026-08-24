"""
Merged Original-vs-Reference baseline analysis across the full 120-task working
set (original 60, 3-fork re-measurement + new60, 1-fork re-measurement).

Same pps/Mann-Whitney U/Vargha-Delaney A12 definitions as analyze_baselines.py
(the original-60-only version), generalized to:
  - merge raw per-iteration measurements from however many source files/forks
    a task's data came from (3 files for the original 60, 1 file for new60) --
    np.mean() over however many trial-level means exist works unmodified,
  - load task metadata from effibenchx_final120.json instead of _final60,
  - recompute the significant-reversal exclusion list from the data directly
    (rather than reusing the hardcoded original-60-only exclusion file), so
    new60 reversals (if any) are captured too.

The original 60's numbers are expected to reproduce byte-for-byte the existing
excluded_tasks_reversed_baseline.json (9 tasks) -- this is checked and printed
as a sanity check before trusting the new60 additions.
"""
import json
import glob
import os

import numpy as np
from scipy.stats import mannwhitneyu

BASE = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
VM_RESULTS_DIR = os.path.join(BASE, "vm_results")
NEW60_DIR = os.path.join(VM_RESULTS_DIR, "new60")


def vargha_delaney_a12(a, b):
    a = np.asarray(a)
    b = np.asarray(b)
    m, n = len(a), len(b)
    all_vals = np.concatenate([a, b])
    ranks = np.empty(len(all_vals))
    order = np.argsort(all_vals)
    ranks[order] = np.arange(1, len(all_vals) + 1)
    sorted_vals = all_vals[order]
    i = 0
    while i < len(sorted_vals):
        j = i
        while j + 1 < len(sorted_vals) and sorted_vals[j + 1] == sorted_vals[i]:
            j += 1
        if j > i:
            avg_rank = ranks[order[i:j + 1]].mean()
            ranks[order[i:j + 1]] = avg_rank
        i = j + 1
    r_a = ranks[:m].sum()
    return (r_a / m - (m + 1) / 2) / n


def load_entries(paths):
    """benchmark_name -> flat raw per-iteration list, and -> list of per-file trial means."""
    raw, means = {}, {}
    for p in paths:
        with open(p) as fh:
            entries = json.load(fh)
        for entry in entries:
            name = entry["benchmark"]
            pm = entry["primaryMetric"]
            flat_raw = [v for fork_data in pm["rawData"] for v in fork_data]
            raw.setdefault(name, []).extend(flat_raw)
            means.setdefault(name, []).append(pm["score"])
    return raw, means


def main():
    orig60_trial_paths = sorted(glob.glob(os.path.join(VM_RESULTS_DIR, "trial*.json")))
    new60_baseline_paths = [os.path.join(NEW60_DIR, "new60_baseline_20260810_102638.json")]

    print(f"Original-60 baseline: {len(orig60_trial_paths)} trial file(s) (3-fork)")
    print(f"New-60 baseline: {len(new60_baseline_paths)} trial file(s) (1-fork)")

    raw60, means60 = load_entries(orig60_trial_paths)
    raw_new, means_new = load_entries(new60_baseline_paths)

    with open(os.path.join(BASE, "effibenchx_final120.json")) as f:
        tasks = json.load(f)
    by_id = {t["id"]: t for t in tasks}

    with open(os.path.join(BASE, "effibenchx_new60.json")) as f:
        new60_ids = {t["id"] for t in json.load(f)}

    rows = []
    for tid in sorted(by_id.keys(), key=int):
        is_new = tid in new60_ids
        raw, means = (raw_new, means_new) if is_new else (raw60, means60)

        ref_name = f"effibenchjmh.task{tid}.Bench.runAllCases"
        orig_name = f"effibenchjmh.task{tid}.original.Bench.runAllCases"
        if ref_name not in raw or orig_name not in raw:
            print(f"  [skip] {tid}: missing benchmark data (ref={ref_name in raw}, orig={orig_name in raw})")
            continue

        ref_raw, orig_raw = raw[ref_name], raw[orig_name]
        ref_mean_of_trials = np.mean(means[ref_name])
        orig_mean_of_trials = np.mean(means[orig_name])

        pps = orig_mean_of_trials / ref_mean_of_trials

        try:
            _, p_value = mannwhitneyu(orig_raw, ref_raw, alternative="two-sided")
        except ValueError:
            p_value = float("nan")
        a12 = vargha_delaney_a12(orig_raw, ref_raw)

        rows.append({
            "id": tid,
            "batch": "new60" if is_new else "orig60",
            "title": by_id[tid]["title"],
            "difficulty": by_id[tid]["difficulty"],
            "n_forks": len(means[ref_name]),
            "original_us": float(orig_mean_of_trials),
            "reference_us": float(ref_mean_of_trials),
            "pps": float(pps),
            "p_value": float(p_value),
            "a12": float(a12),
            "significant": bool(p_value < 0.05) if not np.isnan(p_value) else False,
        })

    print(f"\nAnalyzed {len(rows)} / {len(by_id)} tasks\n")

    # Reversal = Original measured SIGNIFICANTLY FASTER than Reference (a12 < 0.5
    # means orig_raw values tend to be lower i.e. faster than ref_raw, since lower
    # time = faster -- matches analyze_baselines.py's original convention where
    # a12(orig, ref) > 0.5 means Original is slower).
    reversed_rows = [r for r in rows if r["significant"] and r["a12"] < 0.5]
    reversed_ids = sorted((r["id"] for r in reversed_rows), key=int)

    with open(os.path.join(BASE, "excluded_tasks_reversed_baseline.json")) as f:
        existing_excluded = set(json.load(f)["excluded_ids"])
    recomputed_orig60_excluded = {r["id"] for r in reversed_rows if r["batch"] == "orig60"}
    recomputed_new60_excluded = {r["id"] for r in reversed_rows if r["batch"] == "new60"}
    recomputed_all_excluded = recomputed_orig60_excluded | recomputed_new60_excluded

    print("=== Sanity check: recomputed exclusions (both batches) vs. excluded_tasks_reversed_baseline.json ===")
    if recomputed_all_excluded == existing_excluded:
        print(f"  MATCH: {len(existing_excluded)} tasks "
              f"({len(recomputed_orig60_excluded)} orig60 + {len(recomputed_new60_excluded)} new60)")
    else:
        print(f"  MISMATCH! existing={sorted(existing_excluded, key=int)} "
              f"recomputed={sorted(recomputed_all_excluded, key=int)}")

    new60_excluded = sorted(recomputed_new60_excluded, key=int)
    print(f"\n=== New-60 reversals found ===")
    print(f"  {len(new60_excluded)} tasks: {new60_excluded}")
    for r in reversed_rows:
        if r["batch"] == "new60":
            print(f"    id={r['id']:<6} pps={r['pps']:.3f}  p={r['p_value']:.4g}  a12={r['a12']:.3f}  {r['title']}")

    working_set_excluded = existing_excluded | recomputed_all_excluded
    working_set_rows = [r for r in rows if r["id"] not in working_set_excluded]

    print(f"\n=== Combined working set: {len(working_set_rows)} / {len(rows)} tasks "
          f"(excluding {len(working_set_excluded)} total reversals) ===")
    for batch in ("orig60", "new60", None):
        subset = [r for r in working_set_rows if batch is None or r["batch"] == batch]
        label = batch or "ALL (120-task pilot)"
        pps_values = [r["pps"] for r in subset]
        if not pps_values:
            continue
        print(f"  {label:<22} n={len(subset):<4} min={min(pps_values):.2f}  max={max(pps_values):.2f}  "
              f"mean={np.mean(pps_values):.2f}  median={np.median(pps_values):.2f}")

    for diff in ("Easy", "Medium", "Hard"):
        subset = [r for r in working_set_rows if r["difficulty"] == diff]
        pps_values = [r["pps"] for r in subset]
        if not pps_values:
            continue
        print(f"  difficulty={diff:<8} n={len(subset):<4} min={min(pps_values):.2f}  max={max(pps_values):.2f}  "
              f"median={np.median(pps_values):.2f}  avg={np.mean(pps_values):.2f}")

    out = {
        "excluded_ids": sorted(working_set_excluded, key=int),
        "new60_excluded_ids": new60_excluded,
        "orig60_excluded_ids": sorted(existing_excluded, key=int),
        "rows": rows,
    }
    out_path = os.path.join(VM_RESULTS_DIR, "baseline_pps_summary_120.json")
    with open(out_path, "w") as f:
        json.dump(out, f, indent=2)
    print(f"\nFull merged results saved to {out_path}")


if __name__ == "__main__":
    main()
