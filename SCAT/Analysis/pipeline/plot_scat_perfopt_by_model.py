"""
By-model pps comparison, SCAT pilot vs. PerfOpt No-Hint, side by side.

Left panel: SCAT pilot, pooled across difficulty (101-task working set) --
previously panel (b) of Figure fig:scat-difficulty (plot_scat_difficulty_comparison.py).
Right panel: PerfOpt, No-Hint prompt only -- built from the same
representative_benchmark_scores.csv and rename_model()/prompt_map() conventions
already used to generate Figure fig:rq2's published "(b) by Model" panel
(BenchLLMRealSE/Analysis/RQ1/boxplot_summary.py), just restricted to the
NoHint prompt so it is directly comparable to the SCAT panel (which is
single-trial, No-Hint-equivalent by construction). This is a purely
descriptive view over all (benchmark, params, trial) rows for that prompt,
same as the existing Figure fig:rq2(b) -- it does not collapse to one point
per task the way the Wilcoxon/A12 inferential tests in Table tab:effect_size
do, so its median will not exactly equal the point-estimate medians reported
in Figure fig:rq1-benchmark-comparison / RQ1 prose.

Both panels share one model color palette (the SCAT palette already
established in plot_scat_difficulty_comparison.py / plot_rq3_a12_forest.py),
so the same model reads as the same color across the whole figure. Both use
a linear (not log) $y$-axis, each auto-scaled to its own data.
"""
import csv
import json
import os

import matplotlib.pyplot as plt
import numpy as np

BASE = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
FINAL120_PATH = os.path.join(BASE, "effibenchx_final120.json")
LLM_SUMMARY_PATH = os.path.join(BASE, "vm_results", "llm_patch_pps_summary_120.json")
OUT_PATH = os.path.join(os.path.dirname(BASE), "scat_perfopt_by_model.pdf")

PERFOPT_CSV = "/Users/lirongy/Workspace/BenchLLMRealSE/Analysis/preprocessing/representative_benchmark_scores.csv"

MODEL_ORDER = ["o4-mini", "gemini", "deepseek-v3", "deepseek-r1"]
MODEL_LABELS = {"o4-mini": "o4-mini", "gemini": "Gemini", "deepseek-v3": "DS-V3", "deepseek-r1": "DS-R1"}
MODEL_PALETTE = {"o4-mini": "#e2d0e6", "gemini": "#c790c0", "deepseek-v3": "#d0d6ec", "deepseek-r1": "#8994ca"}


def load_scat():
    with open(FINAL120_PATH) as f:
        tasks = json.load(f)
    with open(LLM_SUMMARY_PATH) as f:
        llm_data = json.load(f)
    excluded = set(llm_data["excluded_ids"])
    llm_rows = [r for r in llm_data["pps_rows"] if r["id"] not in excluded]
    return {model: [r["pps"] for r in llm_rows if r["model"] == model] for model in MODEL_ORDER}


def _rename_model(model_name):
    # Same mapping as BenchLLMRealSE/Analysis/RQ1/boxplot_summary.py.
    if "gpt" in model_name.lower():
        return "o4-mini"
    if "gemini" in model_name.lower():
        return "gemini"
    if "deepseek-r1" in model_name.lower():
        return "deepseek-r1"
    if "deepseek-v3" in model_name.lower():
        return "deepseek-v3"
    return model_name


def load_perfopt():
    model_data = {model: [] for model in MODEL_ORDER}
    dev_values = []
    with open(PERFOPT_CSV) as f:
        for row in csv.DictReader(f):
            if row["model"] == "developer":
                dev_values.append(float(row["final_score"]))
            elif row["prompt"] == "prompt1":  # NoHint
                model = _rename_model(row["model"])
                if model in model_data:
                    model_data[model].append(float(row["final_score"]))
    return model_data, dev_values


def _draw_boxes(ax, model_data, ref_value):
    positions = list(range(1, len(MODEL_ORDER) + 1))
    data = [model_data[m] for m in MODEL_ORDER]
    bp = ax.boxplot(data, positions=positions, widths=0.6, patch_artist=True,
                     showfliers=False, medianprops=dict(color="black", linewidth=1.5))
    for patch, model in zip(bp["boxes"], MODEL_ORDER):
        patch.set_facecolor(MODEL_PALETTE[model])
        patch.set_edgecolor("black")
        patch.set_linewidth(0.9)
    for element in ["whiskers", "caps"]:
        for artist in bp[element]:
            artist.set_color("black")
            artist.set_linewidth(0.9)

    ax.grid(True, axis="y", which="major", color="#eaeaea", linewidth=0.8, zorder=-1)
    # Drawn after the boxes with a high zorder so both reference lines stay
    # visible where they cross a box, instead of being hidden under its fill.
    ax.axhline(y=1.0, color="red", linestyle="--", linewidth=1.2, alpha=0.8, zorder=5)
    ax.axhline(y=ref_value, color="blue", linestyle=":", linewidth=1.6, zorder=5)
    ax.tick_params(axis="y", labelsize=12)
    ax.set_xticks(positions)
    return bp


def _draw_panel(ax, model_data, ref_value, title):
    _draw_boxes(ax, model_data, ref_value)
    ax.set_xticklabels([MODEL_LABELS[m] for m in MODEL_ORDER], fontsize=14, rotation=15)
    ax.set_title(title, fontsize=18, pad=12)


def _draw_broken_panel(ax_top, ax_bot, model_data, ref_value, title, bottom_max, top_min, top_max):
    """Broken y-axis: ax_bot shows [0, bottom_max] (where the box bodies
    live), ax_top shows [top_min, top_max] (just the long whisker tail); the
    [bottom_max, top_min] gap is cut out entirely, with a diagonal break mark
    at the seam. Avoids the long SCAT whiskers (up to ~25) squashing the
    IQR/median detail into a thin band at the bottom of a single linear
    axis."""
    _draw_boxes(ax_bot, model_data, ref_value)
    _draw_boxes(ax_top, model_data, ref_value)

    ax_bot.set_ylim(0, bottom_max)
    ax_top.set_ylim(top_min, top_max)

    # Only the seam between the two sub-axes is hidden (needed for the break
    # to read correctly); the outer frame (left/right/top-of-top/bottom-of-bot)
    # stays on all four sides, matching panel (b).
    ax_top.spines["bottom"].set_visible(False)
    ax_bot.spines["top"].set_visible(False)
    ax_top.tick_params(bottom=False, labelbottom=False)
    ax_bot.set_xticklabels([MODEL_LABELS[m] for m in MODEL_ORDER], fontsize=14, rotation=15)
    ax_top.set_title(title, fontsize=18, pad=12)

    # Diagonal break marks at the seam, drawn in axes-fraction coordinates
    # so they sit correctly regardless of the data range on either side.
    d = 0.012
    kwargs = dict(transform=ax_top.transAxes, color="k", clip_on=False, linewidth=1)
    ax_top.plot((-d, +d), (-d, +d), **kwargs)
    kwargs.update(transform=ax_bot.transAxes)
    ax_bot.plot((-d, +d), (1 - d, 1 + d), **kwargs)


def main():
    scat_data = load_scat()
    perfopt_data, dev_values = load_perfopt()
    scat_ref_median = 2.517  # pooled Reference median, Table tab:scat-baseline / Figure fig:scat-difficulty(b)
    dev_median = float(np.median(dev_values))

    fig = plt.figure(figsize=(11, 5.5))
    gs = fig.add_gridspec(2, 2, height_ratios=[1, 3], width_ratios=[1, 1], hspace=0.08, wspace=0.28)
    ax_l_top = fig.add_subplot(gs[0, 0])
    ax_l_bot = fig.add_subplot(gs[1, 0], sharex=ax_l_top)
    ax_r = fig.add_subplot(gs[:, 1])

    _draw_broken_panel(ax_l_top, ax_l_bot, scat_data, scat_ref_median, "(a) SCAT",
                        bottom_max=14, top_min=20, top_max=27)
    ax_l_bot.set_ylabel("$pps$ (vs. original)", fontsize=15)

    _draw_panel(ax_r, perfopt_data, dev_median, "(b) PerfOpt (No-Hint)")
    ax_r.set_ylabel("$pps$ (vs. original)", fontsize=15)

    line_handles = [
        plt.Line2D([0], [0], color="red", linestyle="--", linewidth=1.2, alpha=0.8, label="original"),
        plt.Line2D([0], [0], color="blue", linestyle=":", linewidth=1.6, label="Human-authored baseline median\n(reference / developer)"),
    ]
    fig.legend(handles=line_handles, loc="upper center", bbox_to_anchor=(0.5, 1.1),
               ncol=2, fontsize=13, frameon=False)

    fig.savefig(OUT_PATH, bbox_inches="tight")
    print(f"Saved {OUT_PATH}")

    print(f"\nSCAT Reference median (pooled) = {scat_ref_median}")
    for model in MODEL_ORDER:
        v = scat_data[model]
        print(f"  SCAT    {MODEL_LABELS[model]:<8} n={len(v):<4} median={np.median(v):.3f}")
    print(f"\nPerfOpt Developer median (No-Hint rows n={len(dev_values)}) = {dev_median:.3f}")
    for model in MODEL_ORDER:
        v = perfopt_data[model]
        print(f"  PerfOpt {MODEL_LABELS[model]:<8} n={len(v):<4} median={np.median(v):.3f}")


if __name__ == "__main__":
    main()
