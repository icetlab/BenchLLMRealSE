"""
Recompute Table tab:perfopt's per-project LOC Modified and Speedup stats
from source, as a sanity check before adding a Total row.

LOC Modified: same convention as Table tab:scat-baseline's new column
(added+removed diff lines, from Dataset/baseline_raw/{project}/dev/*.diff),
reusing parse_diff_file from Analysis/RQ2/patch_complexity.py.
Speedup: developer 'final_score' per task from
Analysis/preprocessing/representative_benchmark_scores.csv.
"""
import csv
import glob
import os
import sys

import numpy as np

BENCH_ROOT = "/Users/lirongy/Workspace/BenchLLMRealSE"
sys.path.insert(0, os.path.join(BENCH_ROOT, "Analysis", "RQ2"))
from patch_complexity import parse_diff_file  # noqa: E402

PROJECTS = {"Apache Kafka": "kafka", "Netty": "netty", "Presto": "presto", "RoaringBitmap": "RoaringBitmap"}
CSV_PROJECT_KEY = {"Apache Kafka": "kafka", "Netty": "netty", "Presto": "presto", "RoaringBitmap": "RoaringBitmap"}


def load_loc_modified():
    by_project = {}
    for label, dirname in PROJECTS.items():
        pattern = os.path.join(BENCH_ROOT, "Dataset", "baseline_raw", dirname, "dev", "*.diff")
        vals = []
        for diff_file in sorted(glob.glob(pattern)):
            m = parse_diff_file(diff_file)
            if m:
                vals.append(m["loc_modified"])
        by_project[label] = vals
    return by_project


def load_speedup():
    by_project = {label: [] for label in PROJECTS}
    csv_path = os.path.join(BENCH_ROOT, "Analysis", "preprocessing", "representative_benchmark_scores.csv")
    with open(csv_path) as f:
        for row in csv.DictReader(f):
            if row["model"] != "developer":
                continue
            for label, key in CSV_PROJECT_KEY.items():
                if row["project"] == key:
                    by_project[label].append(float(row["final_score"]))
    return by_project


def main():
    loc = load_loc_modified()
    spd = load_speedup()

    print(f"{'Project':<14}{'#Tasks(LOC)':<12}{'LOC Min':<9}{'LOC Max':<9}{'LOC Avg':<9}"
          f"{'#Tasks(Spd)':<12}{'Spd Min':<9}{'Spd Max':<12}{'Spd Avg':<10}")
    all_loc, all_spd = [], []
    for label in PROJECTS:
        l = loc[label]
        s = spd[label]
        all_loc.extend(l)
        all_spd.extend(s)
        print(f"{label:<14}{len(l):<12}{min(l):<9}{max(l):<9}{np.mean(l):<9.2f}"
              f"{len(s):<12}{min(s):<9.2f}{max(s):<12.2f}{np.mean(s):<10.2f}")

    print("\n=== TOTAL ===")
    print(f"LOC:    n={len(all_loc)} min={min(all_loc)} max={max(all_loc)} avg={np.mean(all_loc):.2f}")
    print(f"Speedup: n={len(all_spd)} min={min(all_spd):.2f} max={max(all_spd):.2f} avg={np.mean(all_spd):.2f}")


if __name__ == "__main__":
    main()
