"""
Redraws the paper's "Overview of the research process" figure (workflow.pdf),
extending the original 3-phase PerfOpt-only pipeline with the SCAT pilot (RQ3):
  - Phase 1 gets a second row: EffiBench-X sampling/baseline-validation feeding
    a second dataset (SCAT, 51 tasks) alongside PerfOpt (65 tasks).
  - Both datasets converge into the shared Phase 2 (prompting/generation/repair)
    and Phase 3 (JMH execution) infrastructure -- matching the paper's own
    "reused unchanged" framing in Section 3.5 (SCAT Pilot Construction).
  - Phase 3 gets a third output branch (Cross-Dataset Comparison -> RQ3),
    mirroring the existing Patch Labeling -> RQ2 branch.

No editable source (e.g. draw.io/pptx) for the original diagram was found, so
this is a full redraw in matplotlib, matching the original's color language:
blue = process step, green = dataset/output artifact, yellow = manual
classification step, orange = context bundle, pink = decision, purple = RQ
outcome.
"""
import matplotlib.pyplot as plt
from matplotlib.patches import FancyBboxPatch, FancyArrowPatch, PathPatch, Ellipse, Rectangle
from matplotlib.path import Path
import matplotlib.patheffects as pe

BLUE = "#BBDEFB"
GREEN = "#C8E6C9"
YELLOW = "#FFF9C4"
ORANGE = "#FFE0B2"
PINK = "#F8BBD0"
PURPLE = "#D1C4E9"
PHASE_BG = "#F2F2F2"
EDGE = "#333333"

fig, ax = plt.subplots(figsize=(16, 9))
ax.set_xlim(0, 16.4)
ax.set_ylim(0, 9.2)
ax.axis("off")


def phase_container(x0, x1, label):
    ax.add_patch(FancyBboxPatch((x0, 0.25), x1 - x0, 8.6, boxstyle="round,pad=0,rounding_size=0.15",
                                 facecolor=PHASE_BG, edgecolor="none", zorder=0))
    ax.text((x0 + x1) / 2, 8.55, label, ha="center", va="top", fontsize=13, fontweight="bold")


def box(cx, cy, w, h, text, color, fontsize=9.5, fontweight="bold", style="round,pad=0,rounding_size=0.08"):
    ax.add_patch(FancyBboxPatch((cx - w / 2, cy - h / 2), w, h, boxstyle=style,
                                 facecolor=color, edgecolor=EDGE, linewidth=1.1, zorder=2))
    ax.text(cx, cy, text, ha="center", va="center", fontsize=fontsize, fontweight=fontweight,
            wrap=True, zorder=3)
    return (cx, cy, w, h)


def arrow(p1, p2, color="black", style="-", lw=1.6, connectionstyle="arc3,rad=0.0", label=None):
    a = FancyArrowPatch(p1, p2, arrowstyle="-|>", mutation_scale=14, color=color,
                         linewidth=lw, linestyle=style, connectionstyle=connectionstyle, zorder=1)
    ax.add_patch(a)
    if label:
        mx, my = (p1[0] + p2[0]) / 2, (p1[1] + p2[1]) / 2
        ax.text(mx + 0.15, my, label, fontsize=8, color=color, style="italic", ha="left")


def right_edge(b):
    cx, cy, w, h = b
    return (cx + w / 2, cy)


def left_edge(b):
    cx, cy, w, h = b
    return (cx - w / 2, cy)


def top_edge(b):
    cx, cy, w, h = b
    return (cx, cy + h / 2)


def bottom_edge(b):
    cx, cy, w, h = b
    return (cx, cy - h / 2)


def draw_doc_icon(cx, cy):
    """Small code-file icon: folded-corner rectangle with '</>' mark."""
    w, h, fold = 0.62, 0.78, 0.16
    x0, y0 = cx - w / 2, cy - h / 2
    path = Path([
        (x0, y0), (x0, y0 + h), (x0 + w - fold, y0 + h), (x0 + w, y0 + h - fold),
        (x0 + w, y0), (x0, y0),
    ])
    ax.add_patch(PathPatch(path, facecolor="white", edgecolor=EDGE, linewidth=1.1, zorder=2))
    ax.add_patch(PathPatch(Path([(x0 + w - fold, y0 + h), (x0 + w - fold, y0 + h - fold),
                                  (x0 + w, y0 + h - fold)]),
                            facecolor="none", edgecolor=EDGE, linewidth=0.9, zorder=2))
    ax.text(cx, cy - 0.05, "</>", ha="center", va="center", fontsize=8, fontweight="bold", zorder=3)


def draw_cylinder_icon(cx, cy):
    """Small database-cylinder icon."""
    w, h, top_h = 0.6, 0.62, 0.16
    ax.add_patch(Rectangle((cx - w / 2, cy - h / 2 + top_h / 2), w, h - top_h,
                            facecolor="white", edgecolor=EDGE, linewidth=1.1, zorder=2))
    ax.add_patch(Ellipse((cx, cy - h / 2 + top_h / 2), w, top_h, facecolor="white",
                          edgecolor=EDGE, linewidth=1.0, zorder=2.1))
    ax.add_patch(Ellipse((cx, cy + h / 2 - top_h / 2), w, top_h, facecolor="white",
                          edgecolor=EDGE, linewidth=1.0, zorder=2.2))


# ---------------------------------------------------------------- Phase 1
phase_container(0.3, 5.5, "Phase 1: Dataset Construction")

draw_doc_icon(1.35, 7.55)
ax.text(1.35, 6.95, "(Java Projects /\n3000+ PRs)", fontsize=8, ha="center", va="center")

b_hotspot = box(3.5, 7.55, 1.7, 0.85, "Identify\nPerformance\nHotspots", BLUE)
b_valid = box(3.5, 6.25, 1.7, 0.7, "Validation\n(Unit Tests & JMH Exec)", BLUE, fontsize=8.3)
b_perfopt = box(3.5, 5.05, 1.7, 0.7, "PerfOpt Dataset\n(65 Tasks)", GREEN)
b_taxonomy = box(1.35, 5.05, 1.55, 0.75, "Taxonomy\nClassification\n[Memory, API, Algo, Sync]", YELLOW, fontsize=7.3)

arrow((1.9, 7.55), left_edge(b_hotspot))
arrow(bottom_edge(b_hotspot), top_edge(b_valid))
arrow(bottom_edge(b_valid), top_edge(b_perfopt))
arrow(left_edge(b_perfopt), right_edge(b_taxonomy), style="--", color="#8a8a5c")

# --- SCAT row (new) ---
draw_cylinder_icon(1.35, 3.15)
ax.text(1.35, 2.55, "EffiBench-X\n(338 Tasks)", fontsize=8, ha="center", va="center")

b_sample = box(3.5, 3.15, 1.7, 0.85, "Stratified\nSampling\n(Easy/Med/Hard)", BLUE, fontsize=8.3)
b_basevalid = box(3.5, 1.95, 1.7, 0.7, "Baseline Validation\n(3-trial JMH)", BLUE, fontsize=8.3)
b_scat = box(3.5, 0.85, 1.7, 0.7, "SCAT Dataset\n(51 Tasks)", GREEN)

arrow((1.9, 3.15), left_edge(b_sample))
arrow(bottom_edge(b_sample), top_edge(b_basevalid))
arrow(bottom_edge(b_basevalid), top_edge(b_scat))

ax.text(3.5, 4.15, "(RQ3 pilot; reuses Phase 2/3 infrastructure, No-Hint only)",
        fontsize=7.3, ha="center", va="center", style="italic", color="#555555")

# ---------------------------------------------------------------- Phase 2
phase_container(5.8, 10.9, "Phase 2: Context-Aware Synthesis")

# stacked context cards
for dx, dy in [(0.12, 0.12), (0.06, 0.06), (0, 0)]:
    ax.add_patch(FancyBboxPatch((7.0 - 0.75 + dx, 7.15 - 0.7 + dy), 1.5, 1.4,
                                 boxstyle="round,pad=0,rounding_size=0.06",
                                 facecolor=ORANGE, edgecolor=EDGE, linewidth=1.0, zorder=2 + dx))
b_context_center = (7.0, 7.15)
ax.text(7.0, 7.15, "Context\n[Source Code,\nUnit Tests,\nJMH Code,\nIssue Desc]", ha="center", va="center",
        fontsize=8, fontweight="bold", zorder=5)

b_promptc = box(7.0, 5.55, 1.7, 0.7, "Prompt\nConstruction", BLUE)
b_callllm = box(9.6, 7.15, 1.9, 0.95, "Call LLMs\n[OpenAI o4-mini,\nGemini 2.5 Pro,\nDeepSeek V3, R1]", BLUE, fontsize=8.2)
b_genpatch = box(9.6, 5.55, 1.7, 0.7, "Generated\nPatches", GREEN)
b_repair = box(7.0, 3.6, 2.1, 0.7, "Automated Repair Loop\n(Compile/Build & Test)", BLUE, fontsize=8.3)

# diamond for Pass?
diamond_c = (9.6, 3.6)
dw, dh = 0.95, 0.95
diamond_path = Path([(diamond_c[0], diamond_c[1] + dh / 2), (diamond_c[0] + dw / 2, diamond_c[1]),
                      (diamond_c[0], diamond_c[1] - dh / 2), (diamond_c[0] - dw / 2, diamond_c[1]),
                      (diamond_c[0], diamond_c[1] + dh / 2)])
ax.add_patch(PathPatch(diamond_path, facecolor=PINK, edgecolor=EDGE, linewidth=1.1, zorder=2))
ax.text(*diamond_c, "Pass?", ha="center", va="center", fontsize=9, fontweight="bold", zorder=3)

# merge point from both datasets into Prompt Construction
merge_x = 6.15
arrow(right_edge(b_perfopt), (merge_x, 5.05), connectionstyle="arc3,rad=-0.15")
arrow(right_edge(b_scat), (merge_x, 0.85), connectionstyle="arc3,rad=0.15")
ax.plot([merge_x, merge_x], [0.85, 5.05], color=EDGE, lw=1.6, zorder=1)
arrow((merge_x, 2.95), left_edge(b_promptc), connectionstyle="arc3,rad=0.0")

arrow(top_edge(b_promptc), (7.0, 6.55))
arrow((7.75, 7.15), left_edge(b_callllm))
arrow(bottom_edge(b_callllm), top_edge(b_genpatch))
arrow(bottom_edge(b_genpatch), (9.6, 5.55 - 0.35))
arrow((9.6, 4.9), (7.5, 3.75), connectionstyle="arc3,rad=0.1")
arrow(right_edge(b_repair), (diamond_c[0] - dw / 2, diamond_c[1]))
ax.annotate("Error Feedback", xy=(9.15, 6.9), xytext=(9.9, 3.9), fontsize=8, color="#c0392b",
            style="italic", ha="left",
            arrowprops=dict(arrowstyle="-|>", color="#c0392b", lw=1.4, linestyle="--",
                             connectionstyle="arc3,rad=0.4"))

# ---------------------------------------------------------------- Phase 3
phase_container(11.2, 16.2, "Phase 3: Measurement & Analysis")

b_plausible = box(12.6, 7.55, 1.9, 0.85, "✓ Plausible\nPatches\n(1953 patches)", GREEN, fontsize=8.5)
arrow((10.55, 3.6), (11.75, 7.4), connectionstyle="arc3,rad=-0.25")

b_jmh = box(12.6, 6.05, 1.9, 0.75, "JMH Execution\n(10 Warmup + 50 Meas.)", BLUE, fontsize=8.3)
arrow(bottom_edge(b_plausible), top_edge(b_jmh))

b_rq1 = box(15.15, 6.55, 1.7, 0.75, "RQ1:\nPerformance\nImprovement?", PURPLE, fontsize=8.3)
arrow(right_edge(b_jmh), left_edge(b_rq1), connectionstyle="arc3,rad=-0.15")

b_labeling = box(12.6, 4.35, 1.9, 0.7, "Patch Labeling\n[Match, Align, Diverge]", YELLOW, fontsize=8.2)
arrow(bottom_edge(b_jmh), top_edge(b_labeling))
b_rq2 = box(15.15, 4.35, 1.7, 0.75, "RQ2:\nOptimization\nStrategy?", PURPLE, fontsize=8.3)
arrow(right_edge(b_labeling), left_edge(b_rq2))

b_crossds = box(12.6, 2.15, 1.9, 0.7, "Cross-Dataset\nComparison\n[PerfOpt vs. SCAT]", YELLOW, fontsize=7.8)
arrow(bottom_edge(b_labeling), top_edge(b_crossds))
b_rq3 = box(15.15, 2.15, 1.7, 0.75, "RQ3:\nSCAT vs.\nReal-World?", PURPLE, fontsize=8.3)
arrow(right_edge(b_crossds), left_edge(b_rq3))

plt.tight_layout()
fig.savefig("/Users/lirongy/Workspace/Paper Writing/JSS_version/workflow.pdf", bbox_inches="tight")
print("Saved workflow.pdf")
