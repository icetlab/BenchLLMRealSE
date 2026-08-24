"""
RQ1 figure: dumbbell/forest plot of Vargha-Delaney A12 effect size (LLM patch vs.
the best available human-authored solution -- Developer for PerfOpt, Reference
for the SCAT pilot), one row per model. A12 > 0.5 means the LLM patch
outperforms the human baseline; < 0.5 means the human baseline wins.

PerfOpt values are the published No-Hint "vs. Developer" column of
Table~tab:effect_size in main.tex (single point estimate, no per-task raw data
available here). SCAT values are the per-model median A12 (vs Reference) from
the merged 101-task working set (original 60 + new60, 19 baseline-reversal
tasks excluded), computed in analyze_llm_patches_merged.py
(vm_results/llm_patch_pps_summary_120.json).

Reuses the color palette and box/line styling from
BenchLLMRealSE/Analysis/RQ1/boxplot_summary.py for visual consistency with the
paper's other figures.
"""
import json
import os

import matplotlib.pyplot as plt
import numpy as np

BASE = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
SUMMARY_PATH = os.path.join(BASE, "vm_results", "llm_patch_pps_summary_120.json")
OUT_PATH = os.path.join(os.path.dirname(BASE), "rq3_a12_forest_plot.pdf")

MODEL_ORDER = ["o4-mini", "gemini", "deepseek-v3", "deepseek-r1"]
MODEL_LABELS = {"o4-mini": "o4 mini", "gemini": "Gemini", "deepseek-v3": "DS-V3", "deepseek-r1": "DS-R1"}
PALETTE = {"o4-mini": "#9E9EE0", "gemini": "#FFF2CC", "deepseek-v3": "#D3D3D3", "deepseek-r1": "#90EE90"}

# Published Table tab:effect_size, "vs. Developer", NoHint column.
PERFOPT_A12 = {"o4-mini": 0.142, "gemini": 0.201, "deepseek-v3": 0.076, "deepseek-r1": 0.170}


def load_scat_median_a12():
    with open(SUMMARY_PATH) as f:
        data = json.load(f)
    rows_101 = [r for r in data["pps_rows"] if not r["excluded_baseline_reversal"]]
    out = {}
    for model in MODEL_ORDER:
        vals = [r["a12_vs_reference"] for r in rows_101 if r["model"] == model]
        out[model] = float(np.median(vals))
    return out


def main():
    scat_a12 = load_scat_median_a12()

    fig, ax = plt.subplots(figsize=(9, 5))
    ax.set_facecolor("white")
    ax.grid(True, color="#d9d9d9", linewidth=0.8, zorder=0)
    for spine in ax.spines.values():
        spine.set_visible(False)

    y_positions = list(range(len(MODEL_ORDER)))[::-1]  # o4-mini on top

    for y, model in zip(y_positions, MODEL_ORDER):
        perfopt_v, scat_v = PERFOPT_A12[model], scat_a12[model]
        ax.plot([perfopt_v, scat_v], [y, y], color="black", linewidth=1.4, zorder=2)
        ax.scatter([perfopt_v], [y], s=170, marker="o", color=PALETTE[model],
                   edgecolor="black", linewidth=1.2, zorder=3)
        ax.scatter([scat_v], [y], s=170, marker="D", color=PALETTE[model],
                   edgecolor="black", linewidth=1.2, zorder=3)
        ax.text(perfopt_v, y + 0.22, f"{perfopt_v:.3f}", ha="center", va="bottom",
                fontsize=13, color="black", zorder=4)
        ax.text(scat_v, y + 0.22, f"{scat_v:.3f}", ha="center", va="bottom",
                fontsize=13, color="black", zorder=4)

    ax.axvline(x=0.5, color="r", linestyle="--", linewidth=1.2, zorder=1)
    ax.text(0.5, len(MODEL_ORDER) - 0.35, "no difference", color="r",
            ha="center", va="bottom", fontsize=14)

    ax.text(0.02, -0.9, "← human baseline wins", ha="left", va="top", fontsize=13, color="dimgray")
    ax.text(0.98, -0.9, "LLM patch wins →", ha="right", va="top", fontsize=13, color="dimgray")

    ax.set_yticks(y_positions)
    ax.set_yticklabels([MODEL_LABELS[m] for m in MODEL_ORDER], fontsize=17)
    # Padded beyond [0, 1] so markers near the bounds (e.g. DS-R1 at 0.996) aren't
    # clipped by the axes box -- A12 itself is still only ever plotted in [0, 1].
    ax.set_xlim(-0.04, 1.04)
    ax.set_xticks([0.0, 0.2, 0.4, 0.6, 0.8, 1.0])
    ax.set_ylim(-1.3, len(MODEL_ORDER) - 0.3)
    ax.set_xlabel(r"$\hat{A}_{12}$ (LLM patch vs. human-authored ceiling)", fontsize=16)
    ax.tick_params(axis="x", labelsize=14)

    circle_handle = plt.Line2D([0], [0], marker="o", color="w", markerfacecolor="white",
                                markeredgecolor="black", markersize=11, label="PerfOpt (vs. Developer)")
    diamond_handle = plt.Line2D([0], [0], marker="D", color="w", markerfacecolor="white",
                                 markeredgecolor="black", markersize=10, label="SCAT (vs. Reference)")
    ax.legend(handles=[circle_handle, diamond_handle], loc="upper center",
              bbox_to_anchor=(0.5, 1.15), ncol=2, fontsize=13, frameon=False)

    fig.tight_layout()
    fig.savefig(OUT_PATH, bbox_inches="tight")
    print(f"Saved {OUT_PATH}")


if __name__ == "__main__":
    main()
