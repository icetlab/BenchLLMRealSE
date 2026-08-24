"""
RQ1 figure: matched comparison of LLM patch plausibility on PerfOpt (No-Hint)
vs. the EffiBench-X (SCAT) pilot, one lollipop pair per model.

Values are annotated directly on each point (previously only available in the
now-removed Table "SCAT vs. PerfOpt" table -- folding them into the figure
avoids reporting the same numbers in both a table and a figure).

PerfOpt No-Hint values: published Table tab:patch_count (plausibility).
SCAT values: 120-task plausible rate, computed in analyze_llm_patches_merged.py
(vm_results/llm_patch_pps_summary_120.json).

The performance-score comparison previously shown as panel (b) here now lives
in plot_scat_perfopt_by_model.py, as a by-model boxplot paired against the
SCAT by-model view (Figure fig:scat-difficulty's former panel (b)).

Matches the color palette and general lollipop styling already established
for this figure (blue circle = PerfOpt No-Hint, orange square = EffiBench-X
(SCAT), connected by a gray line).
"""
import os

import matplotlib.pyplot as plt

BASE = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
OUT_PATH = os.path.join(os.path.dirname(BASE), "rq1_benchmark_comparison.pdf")

MODEL_ORDER = ["o4-mini", "gemini", "deepseek-v3", "deepseek-r1"]
MODEL_LABELS = {"o4-mini": "o4-mini", "gemini": "Gemini 2.5 Pro", "deepseek-v3": "DeepSeek-V3", "deepseek-r1": "DeepSeek-R1"}

PERFOPT_COLOR = "#414242"   # dark charcoal circle
SCAT_COLOR = "#8994ca"      # periwinkle square
LINE_COLOR = "#d0d6ec"

# Published Table tab:patch_count, "No Hint" column (plausible rate).
PERFOPT_PLAUSIBLE = {"o4-mini": 59.0, "gemini": 65.0, "deepseek-v3": 61.0, "deepseek-r1": 62.0}

# vm_results/llm_patch_pps_summary_120.json: plausible_rates (all 120 tasks).
SCAT_PLAUSIBLE = {"o4-mini": 88.3, "gemini": 98.3, "deepseek-v3": 90.0, "deepseek-r1": 97.5}


def main():
    fig, ax = plt.subplots(figsize=(6.5, 5))

    x_positions = list(range(len(MODEL_ORDER)))
    for x, model in zip(x_positions, MODEL_ORDER):
        p_v, s_v = PERFOPT_PLAUSIBLE[model], SCAT_PLAUSIBLE[model]
        ax.plot([x, x], [p_v, s_v], color=LINE_COLOR, linewidth=2, zorder=1)
        ax.scatter([x], [p_v], s=140, marker="o", color=PERFOPT_COLOR,
                   edgecolor="black", linewidth=0.8, zorder=3)
        ax.scatter([x], [s_v], s=140, marker="s", color=SCAT_COLOR,
                   edgecolor="black", linewidth=0.8, zorder=3)

        y_range = max(SCAT_PLAUSIBLE.values()) - min(PERFOPT_PLAUSIBLE.values())
        pad = y_range * 0.05
        ax.text(x, p_v - pad, f"{p_v:.1f}%", ha="center", va="top",
                fontsize=13, color=PERFOPT_COLOR, zorder=4)
        ax.text(x, s_v + pad, f"{s_v:.1f}%", ha="center", va="bottom",
                fontsize=13, color=SCAT_COLOR, zorder=4)

    ax.set_xticks(x_positions)
    ax.set_xticklabels([MODEL_LABELS[m] for m in MODEL_ORDER], rotation=20, ha="right", fontsize=15)
    ax.set_ylabel("Plausible patches (%)", fontsize=16)
    ax.grid(True, axis="y", color="#e0e0e0", linewidth=0.8, zorder=0)
    ax.tick_params(axis="y", labelsize=13)
    for spine in ["top", "right"]:
        ax.spines[spine].set_visible(False)
    ax.set_xlim(-0.55, len(MODEL_ORDER) - 0.4)
    ax.set_ylim(50, 103)

    circle_handle = plt.Line2D([0], [0], marker="o", color="w", markerfacecolor=PERFOPT_COLOR,
                                markeredgecolor="black", markersize=10, label="PerfOpt No-Hint")
    square_handle = plt.Line2D([0], [0], marker="s", color="w", markerfacecolor=SCAT_COLOR,
                                markeredgecolor="black", markersize=10, label="EffiBench-X (SCAT)")
    fig.legend(handles=[circle_handle, square_handle], loc="upper center",
               bbox_to_anchor=(0.5, 1.05), ncol=2, fontsize=14, frameon=False)

    fig.tight_layout(rect=[0, 0, 1, 0.93])
    fig.savefig(OUT_PATH, bbox_inches="tight")
    print(f"Saved {OUT_PATH}")


if __name__ == "__main__":
    main()
