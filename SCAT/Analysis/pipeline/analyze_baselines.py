"""
Analyzes the VM-collected JMH results (Original vs Reference solution, 3 trials
each) for all 60 EffiBench-X pilot tasks: computes the patch performance score
(pps) using the same definition as the main paper (Section 3.4.1), plus a
Mann-Whitney U test + Vargha-Delaney A12 effect size per task to characterize
how reliable/large the Original-vs-Reference gap is under measurement noise.

Mann-Whitney U (not Wilcoxon signed-rank) is used here because Original and
Reference are two different code variants measured independently across
trials/iterations -- there is no natural per-iteration pairing between them
(unlike comparing an LLM patch's iterations against a shared baseline measured
in the exact same trial sequence, which is a paired design).
"""
import json
import glob
import os
import sys

import numpy as np
from scipy.stats import mannwhitneyu

BASE = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
VM_RESULTS_DIR = os.path.join(BASE, "vm_results")


def load_all_trials():
    trials = []
    for f in sorted(glob.glob(os.path.join(VM_RESULTS_DIR, "trial*.json"))):
        with open(f) as fh:
            trials.append(json.load(fh))
    return trials


def collect_raw_scores(trials):
    """benchmark_name -> flat list of ALL raw per-iteration scores across all 3 trials."""
    raw = {}
    means = {}
    for trial in trials:
        for entry in trial:
            name = entry["benchmark"]
            pm = entry["primaryMetric"]
            flat_raw = [v for fork_data in pm["rawData"] for v in fork_data]
            raw.setdefault(name, []).extend(flat_raw)
            means.setdefault(name, []).append(pm["score"])
    return raw, means


def vargha_delaney_a12(a, b):
    """A12: probability that a random value from `a` is greater than one from `b`
    (+ 0.5 * probability of ties). a, b: array-like of raw measurements."""
    a = np.asarray(a)
    b = np.asarray(b)
    m, n = len(a), len(b)
    # rank-based formula (equivalent to counting pairwise comparisons, faster than O(m*n) for large n)
    all_vals = np.concatenate([a, b])
    ranks = np.empty(len(all_vals))
    order = np.argsort(all_vals)
    ranks[order] = np.arange(1, len(all_vals) + 1)
    # handle ties by averaging ranks
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
    a12 = (r_a / m - (m + 1) / 2) / n
    return a12


def main():
    trials = load_all_trials()
    print(f"Loaded {len(trials)} trials")
    raw, means = collect_raw_scores(trials)

    with open(os.path.join(BASE, "effibenchx_final60.json")) as f:
        tasks = json.load(f)
    by_id = {t["id"]: t for t in tasks}

    rows = []
    for tid in sorted(by_id.keys(), key=int):
        ref_name = f"effibenchjmh.task{tid}.Bench.runAllCases"
        orig_name = f"effibenchjmh.task{tid}.original.Bench.runAllCases"
        if ref_name not in raw or orig_name not in raw:
            print(f"  [skip] {tid}: missing benchmark data (ref={ref_name in raw}, orig={orig_name in raw})")
            continue

        ref_raw, orig_raw = raw[ref_name], raw[orig_name]
        ref_mean_of_trials = np.mean(means[ref_name])   # mean of the 3 trial-level means
        orig_mean_of_trials = np.mean(means[orig_name])

        # avgt mode (time per op) -> lower is better -> pps = orig/new
        pps = orig_mean_of_trials / ref_mean_of_trials

        try:
            _, p_value = mannwhitneyu(orig_raw, ref_raw, alternative="two-sided")
        except ValueError:
            p_value = float("nan")
        a12 = vargha_delaney_a12(orig_raw, ref_raw)  # >0.5 means Original is SLOWER (higher time) than Reference

        rows.append({
            "id": tid,
            "title": by_id[tid]["title"],
            "difficulty": by_id[tid]["difficulty"],
            "original_us": float(orig_mean_of_trials),
            "reference_us": float(ref_mean_of_trials),
            "pps": float(pps),
            "p_value": float(p_value),
            "a12": float(a12),
            "significant": bool(p_value < 0.05) if not np.isnan(p_value) else False,
        })

    print(f"\nAnalyzed {len(rows)} / {len(by_id)} tasks\n")

    pps_values = [r["pps"] for r in rows]
    print("=== pps summary across all tasks ===")
    print(f"  min={min(pps_values):.2f}  max={max(pps_values):.2f}  "
          f"mean={np.mean(pps_values):.2f}  median={np.median(pps_values):.2f}")

    sig_rows = [r for r in rows if r["significant"]]
    print(f"\n  Statistically significant Original-vs-Reference gap (p<0.05, Mann-Whitney U): "
          f"{len(sig_rows)} / {len(rows)} tasks")

    print("\n=== Top 10 by pps (Reference much faster than Original) ===")
    for r in sorted(rows, key=lambda x: -x["pps"])[:10]:
        print(f"  id={r['id']:<6} pps={r['pps']:>10.2f}  p={r['p_value']:.4g}  A12={r['a12']:.3f}  {r['title']}")

    print("\n=== Bottom 10 by pps (smallest gap / possibly noisy or reversed) ===")
    for r in sorted(rows, key=lambda x: x["pps"])[:10]:
        print(f"  id={r['id']:<6} pps={r['pps']:>10.4f}  p={r['p_value']:.4g}  A12={r['a12']:.3f}  {r['title']}")

    out_path = os.path.join(VM_RESULTS_DIR, "baseline_pps_summary.json")
    with open(out_path, "w") as f:
        json.dump(rows, f, indent=2)
    print(f"\nFull per-task results saved to {out_path}")


if __name__ == "__main__":
    main()
