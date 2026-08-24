"""
SCAT pilot figure: boxplot of pps (vs. Original) by task difficulty
(Easy/Medium/Hard), five boxes per difficulty -- Reference and each of the
four LLM models (plausible patches only) -- on the 101-task baseline-validated
working set.

Log-scale y-axis and IQR-based outlier exclusion match the convention already
used for pps boxplots elsewhere in the paper (Figure fig:rq1, panel (c);
Figure fig:boxplot_classification_analysis), since pps spans several orders
of magnitude (see Table tab:scat-baseline).

Reference values: vm_results/baseline_pps_summary_120.json (Table tab:scat-baseline).
LLM patch values: vm_results/llm_patch_pps_summary_120.json (working set = not
in excluded_ids), one box per model.

The by-model view (pooled across difficulty) previously lived here as panel
(b); it now lives in plot_scat_perfopt_by_model.py, paired against PerfOpt's
No-Hint by-model results instead.
"""
import json
import os

import matplotlib.pyplot as plt
import numpy as np

BASE = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
FINAL120_PATH = os.path.join(BASE, "effibenchx_final120.json")
LLM_SUMMARY_PATH = os.path.join(BASE, "vm_results", "llm_patch_pps_summary_120.json")
BASELINE_SUMMARY_PATH = os.path.join(BASE, "vm_results", "baseline_pps_summary_120.json")
OUT_PATH = os.path.join(os.path.dirname(BASE), "scat_difficulty_comparison.pdf")

DIFFICULTY_ORDER = ["Easy", "Medium", "Hard"]
MODEL_ORDER = ["o4-mini", "gemini", "deepseek-v3", "deepseek-r1"]
MODEL_LABELS = {"o4-mini": "o4-mini", "gemini": "Gemini", "deepseek-v3": "DS-V3", "deepseek-r1": "DS-R1"}
SERIES_ORDER = ["reference"] + MODEL_ORDER
SERIES_LABELS = {"reference": "reference", **MODEL_LABELS}

REFERENCE_COLOR = "#FFFFFF"
MODEL_PALETTE = {"o4-mini": "#e2d0e6", "gemini": "#c790c0", "deepseek-v3": "#d0d6ec", "deepseek-r1": "#8994ca"}
SERIES_COLORS = {"reference": REFERENCE_COLOR, **MODEL_PALETTE}


def load_series():
    with open(FINAL120_PATH) as f:
        tasks = json.load(f)
    by_id = {t["id"]: t for t in tasks}

    with open(LLM_SUMMARY_PATH) as f:
        llm_data = json.load(f)
    excluded = set(llm_data["excluded_ids"])
    llm_rows = [r for r in llm_data["pps_rows"] if r["id"] not in excluded]

    with open(BASELINE_SUMMARY_PATH) as f:
        baseline_data = json.load(f)
    baseline_rows = [r for r in baseline_data["rows"] if r["id"] not in excluded]

    series = {diff: {} for diff in DIFFICULTY_ORDER}
    for diff in DIFFICULTY_ORDER:
        series[diff]["reference"] = [r["pps"] for r in baseline_rows if r["difficulty"] == diff]
        for model in MODEL_ORDER:
            series[diff][model] = [r["pps"] for r in llm_rows
                                    if by_id[r["id"]]["difficulty"] == diff and r["model"] == model]
    return series


def main():
    series = load_series()

    fig, ax = plt.subplots(figsize=(11, 5.5))

    n_series = len(SERIES_ORDER)
    # Each difficulty gets an equal-width segment [gi*SEGMENT_WIDTH, (gi+1)*SEGMENT_WIDTH);
    # its n_series boxes (spaced 1 apart, spanning n_series-1) are centered within that
    # segment, so all three segments -- and the gaps on either side of each -- are identical.
    SEGMENT_WIDTH = n_series + 1.5
    box_span = n_series - 1
    margin = (SEGMENT_WIDTH - box_span) / 2
    positions, data, colors = [], [], []
    group_centers = []

    for gi, diff in enumerate(DIFFICULTY_ORDER):
        segment_start = gi * SEGMENT_WIDTH
        first_box_pos = segment_start + margin
        group_positions = [first_box_pos + i for i in range(n_series)]
        group_centers.append(np.mean(group_positions))
        for pos, key in zip(group_positions, SERIES_ORDER):
            positions.append(pos)
            data.append(series[diff][key])
            colors.append(SERIES_COLORS[key])

    bp = ax.boxplot(data, positions=positions, widths=0.75, patch_artist=True,
                     showfliers=False, medianprops=dict(color="black", linewidth=1.5))
    for patch, color in zip(bp["boxes"], colors):
        patch.set_facecolor(color)
        patch.set_edgecolor("black")
        patch.set_linewidth(0.9)
    for element in ["whiskers", "caps"]:
        for artist in bp[element]:
            artist.set_color("black")
            artist.set_linewidth(0.9)

    ax.set_yscale("log")
    ax.axhline(y=1.0, color="red", linestyle="--", linewidth=1.2, alpha=0.8, zorder=5)
    ax.set_ylabel("$pps$ (vs. original, log scale)", fontsize=17)
    ax.grid(True, axis="y", which="major", color="#eaeaea", linewidth=0.8, zorder=-1)
    ax.tick_params(axis="y", labelsize=14)

    ax.set_xticks(group_centers)
    ax.set_xticklabels(DIFFICULTY_ORDER, fontsize=20)
    ax.set_xlim(0, len(DIFFICULTY_ORDER) * SEGMENT_WIDTH)

    # Vertical separators exactly at each equal-width segment boundary.
    for gi in range(1, len(DIFFICULTY_ORDER)):
        sep_x = gi * SEGMENT_WIDTH
        ax.axvline(x=sep_x, color="#cccccc", linewidth=0.8, zorder=-2)

    # Per-difficulty Reference median, drawn as a short horizontal line spanning
    # only that difficulty's segment (medians differ by difficulty -- 2.49 /
    # 2.35 / 3.69, Table tab:scat-baseline -- so one pooled line would misstate
    # each segment). Drawn after the boxes with a high zorder so it stays
    # visible where it crosses a box, instead of being hidden under its fill.
    for gi, diff in enumerate(DIFFICULTY_ORDER):
        segment_start = gi * SEGMENT_WIDTH
        segment_end = (gi + 1) * SEGMENT_WIDTH
        ref_median = np.median(series[diff]["reference"])
        ax.plot([segment_start, segment_end], [ref_median, ref_median],
                 color="blue", linestyle=":", linewidth=1.6, zorder=5)

    handles = [plt.Rectangle((0, 0), 1, 1, facecolor=SERIES_COLORS[k], edgecolor="black", label=SERIES_LABELS[k])
               for k in SERIES_ORDER]
    handles.append(plt.Line2D([0], [0], color="red", linestyle="--", linewidth=1.2, alpha=0.8, label="original"))
    handles.append(plt.Line2D([0], [0], color="blue", linestyle=":", linewidth=1.6, label="reference median"))
    fig.legend(handles=handles, loc="upper center", bbox_to_anchor=(0.5, 1.03),
               ncol=len(handles), fontsize=14, frameon=False, columnspacing=1.2)

    fig.tight_layout(rect=[0, 0, 1, 0.93])
    fig.savefig(OUT_PATH, bbox_inches="tight")
    print(f"Saved {OUT_PATH}")
    for diff in DIFFICULTY_ORDER:
        for key in SERIES_ORDER:
            vals = series[diff][key]
            print(f"  {diff:<8} {SERIES_LABELS[key]:<10} n={len(vals):<4} median={np.median(vals):.3f}")


if __name__ == "__main__":
    main()
