"""
Merged LLM-patch analysis across the full 120-task pilot (original 60 + new60),
generalizing analyze_llm_patches.py (which only covered the original 60, whose
Original/Reference/all-4-LLM-patches were all measured together in one single-
trial VM run).

new60's data instead comes from four separate VM runs (baseline: Original +
Reference; three incremental LLM-patch runs collecting newly-plausible patches
as generation retries resolved). All are single-fork/single-trial, matching
the original 60's LLM-patch run (this SCAT pilot's LLM-patch comparison was
always single-trial by design -- see Section "Standardized Performance
Measurement"). Merging is a simple dict union keyed by JMH benchmark name,
since naming (effibenchjmh.task{id}.{...}.Bench.runAllCases) is globally
unique and identical across all source files.

Uses the corrected, audited excluded_tasks_reversed_baseline.json (19 ids: 10
orig60 + 9 new60, see analyze_baselines_merged.py) for the working-set filter.
"""
import json
import os

import numpy as np
from scipy.stats import mannwhitneyu

BASE = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
RESULTS_DIR = os.path.join(BASE, "results")
VM_RESULTS_DIR = os.path.join(BASE, "vm_results")
NEW60_DIR = os.path.join(VM_RESULTS_DIR, "new60")

SOURCE_FILES = [
    os.path.join(VM_RESULTS_DIR, "llm_patches", "trial1_20260802_125439.json"),  # orig60: Original+Reference+4 LLM patches
    os.path.join(NEW60_DIR, "new60_baseline_20260810_102638.json"),               # new60: Original+Reference
    os.path.join(NEW60_DIR, "new60_llm_20260810_181049.json"),                    # new60: LLM patches, batch 1 (207)
    os.path.join(NEW60_DIR, "new60_llm_retry3_20260810_214351.json"),             # new60: LLM patches, batch 2 (3)
    os.path.join(NEW60_DIR, "new60_llm_r1retry15_20260811_104718.json"),          # new60: LLM patches, batch 3 (15)
]

OUT_PATH = os.path.join(VM_RESULTS_DIR, "llm_patch_pps_summary_120.json")

with open(os.path.join(BASE, "excluded_tasks_reversed_baseline.json")) as f:
    EXCLUDED_IDS = set(json.load(f)["excluded_ids"])

MODELS = ["o4-mini", "gemini", "deepseek-v3", "deepseek-r1"]
MODEL_PKG_NAME = {
    "o4-mini": "llm_o4mini",
    "gemini": "llm_gemini",
    "deepseek-v3": "llm_deepseekv3",
    "deepseek-r1": "llm_deepseekr1",
}


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


def compute_plausible_rates():
    """Per-model plausible rate over all 120 tasks (480 attempts total)."""
    rates = {}
    all_outcomes = []
    for model in MODELS:
        outcomes = []
        for tid in sorted(os.listdir(RESULTS_DIR)):
            status_path = os.path.join(RESULTS_DIR, tid, model, "trial1", "status.json")
            if not os.path.isfile(status_path):
                continue
            with open(status_path) as f:
                status = json.load(f)
            outcomes.append(status.get("outcome"))
        n = len(outcomes)
        n_plausible = sum(1 for o in outcomes if o == "plausible")
        rates[model] = {"plausible": n_plausible, "total": n, "rate": n_plausible / n if n else float("nan")}
        all_outcomes.extend(outcomes)
    overall_plausible = sum(1 for o in all_outcomes if o == "plausible")
    rates["overall"] = {
        "plausible": overall_plausible, "total": len(all_outcomes),
        "rate": overall_plausible / len(all_outcomes) if all_outcomes else float("nan"),
    }
    return rates


def load_merged_runs(paths):
    raw, means = {}, {}
    for p in paths:
        with open(p) as f:
            entries = json.load(f)
        for entry in entries:
            name = entry["benchmark"]
            pm = entry["primaryMetric"]
            flat_raw = [v for fork_data in pm["rawData"] for v in fork_data]
            if name in raw:
                print(f"  [warn] duplicate benchmark name across source files: {name} (keeping first seen)")
                continue
            raw[name] = flat_raw
            means[name] = pm["score"]
    return raw, means


def compute_pps(raw, means):
    rows = []
    for model in MODELS:
        pkg = MODEL_PKG_NAME[model]
        for tid in sorted(os.listdir(RESULTS_DIR)):
            status_path = os.path.join(RESULTS_DIR, tid, model, "trial1", "status.json")
            if not os.path.isfile(status_path):
                continue
            with open(status_path) as f:
                status = json.load(f)
            if status.get("outcome") != "plausible":
                continue

            orig_name = f"effibenchjmh.task{tid}.original.Bench.runAllCases"
            ref_name = f"effibenchjmh.task{tid}.Bench.runAllCases"
            patch_name = f"effibenchjmh.task{tid}.{pkg}.Bench.runAllCases"
            if orig_name not in raw or ref_name not in raw or patch_name not in raw:
                print(f"  [warn] missing JMH data for {tid}/{model} "
                      f"(orig={orig_name in raw}, ref={ref_name in raw}, patch={patch_name in raw})")
                continue

            orig_raw, ref_raw, patch_raw = raw[orig_name], raw[ref_name], raw[patch_name]
            orig_mean, ref_mean, patch_mean = means[orig_name], means[ref_name], means[patch_name]
            pps = orig_mean / patch_mean
            pps_vs_reference = ref_mean / patch_mean

            try:
                _, p_value = mannwhitneyu(orig_raw, patch_raw, alternative="two-sided")
            except ValueError:
                p_value = float("nan")
            a12 = vargha_delaney_a12(orig_raw, patch_raw)

            try:
                _, p_value_ref = mannwhitneyu(patch_raw, ref_raw, alternative="two-sided")
            except ValueError:
                p_value_ref = float("nan")
            a12_vs_reference = vargha_delaney_a12(ref_raw, patch_raw)

            rows.append({
                "id": tid, "model": model,
                "excluded_baseline_reversal": tid in EXCLUDED_IDS,
                "original_us": float(orig_mean), "reference_us": float(ref_mean), "patch_us": float(patch_mean),
                "pps": float(pps), "p_value": float(p_value), "a12": float(a12),
                "significant": bool(p_value < 0.05) if not np.isnan(p_value) else False,
                "pps_vs_reference": float(pps_vs_reference),
                "p_value_vs_reference": float(p_value_ref), "a12_vs_reference": float(a12_vs_reference),
                "significant_vs_reference": bool(p_value_ref < 0.05) if not np.isnan(p_value_ref) else False,
            })
    return rows


def summarize(rows, label):
    pps_values = [r["pps"] for r in rows]
    if not pps_values:
        print(f"  ({label}: no data)")
        return
    sig = sum(1 for r in rows if r["significant"])
    print(f"  {label}: n={len(rows)}  min={min(pps_values):.3f}  max={max(pps_values):.3f}  "
          f"mean={np.mean(pps_values):.3f}  median={np.median(pps_values):.3f}  "
          f"significant={sig}/{len(rows)}")


def summarize_vs_reference(rows, label):
    if not rows:
        print(f"  ({label}: no data)")
        return
    a12_values = [r["a12_vs_reference"] for r in rows]
    pps_ref_values = [r["pps_vs_reference"] for r in rows]
    beats_ref = sum(1 for r in rows if r["a12_vs_reference"] > 0.5)
    sig_worse = sum(1 for r in rows if r["significant_vs_reference"] and r["a12_vs_reference"] < 0.5)
    sig_better = sum(1 for r in rows if r["significant_vs_reference"] and r["a12_vs_reference"] > 0.5)
    print(f"  {label}: n={len(rows)}  median A12={np.median(a12_values):.3f}  "
          f"median pps_vs_ref={np.median(pps_ref_values):.3f}  "
          f"patch>=ref(A12>0.5)={beats_ref}/{len(rows)}  "
          f"sig. worse than ref={sig_worse}  sig. better than ref={sig_better}")


def main():
    print("=== Plausible rate (all 120 tasks, per model) ===")
    rates = compute_plausible_rates()
    for model in MODELS:
        r = rates[model]
        print(f"  {model:<14} {r['plausible']:>3}/{r['total']:<3} ({r['rate']*100:.1f}%)")
    r = rates["overall"]
    print(f"  {'overall':<14} {r['plausible']:>3}/{r['total']:<3} ({r['rate']*100:.1f}%)")

    print(f"\n=== Loading and merging {len(SOURCE_FILES)} VM result files ===")
    raw, means = load_merged_runs(SOURCE_FILES)
    print(f"  {len(raw)} distinct benchmark names loaded")

    rows = compute_pps(raw, means)
    print(f"  Computed pps for {len(rows)} plausible (task, model) patches")

    rows_working_set = [r for r in rows if not r["excluded_baseline_reversal"]]

    print(f"\n=== pps summary (all plausible patches, 120-task universe) ===")
    summarize(rows, "overall")
    for model in MODELS:
        summarize([r for r in rows if r["model"] == model], model)

    print(f"\n=== pps summary (working set, excluding {len(EXCLUDED_IDS)} baseline-reversal tasks) ===")
    summarize(rows_working_set, "overall")
    for model in MODELS:
        summarize([r for r in rows_working_set if r["model"] == model], model)

    print("\n=== Patch vs Reference (human-authored ceiling), working set ===")
    print("    (A12 computed as vargha_delaney_a12(reference, patch); >0.5 = patch beats Reference)")
    summarize_vs_reference(rows_working_set, "overall")
    for model in MODELS:
        summarize_vs_reference([r for r in rows_working_set if r["model"] == model], model)

    out = {"plausible_rates": rates, "excluded_ids": sorted(EXCLUDED_IDS, key=int), "pps_rows": rows}
    with open(OUT_PATH, "w") as f:
        json.dump(out, f, indent=2)
    print(f"\nFull results saved to {OUT_PATH}")


if __name__ == "__main__":
    main()
