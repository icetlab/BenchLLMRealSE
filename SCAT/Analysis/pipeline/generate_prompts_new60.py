"""
Same as generate_prompts.py, but for the new60 batch. Writes into the same
prompts/{task_id}/prompt.txt tree (additive -- doesn't touch the original 60's
existing prompt files).
"""
import json
import os

from generate_prompts import build_prompt, OUT_DIR

BASE = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))


def main():
    with open(os.path.join(BASE, "effibenchx_new60.json")) as f:
        tasks = json.load(f)

    os.makedirs(OUT_DIR, exist_ok=True)
    for t in tasks:
        tid = t["id"]
        task_dir = os.path.join(OUT_DIR, tid)
        os.makedirs(task_dir, exist_ok=True)
        prompt = build_prompt(t)
        with open(os.path.join(task_dir, "prompt.txt"), "w", encoding="utf-8") as f:
            f.write(prompt)

    print(f"Generated {len(tasks)} prompts (new60) under {OUT_DIR}/{{task_id}}/prompt.txt")


if __name__ == "__main__":
    main()
