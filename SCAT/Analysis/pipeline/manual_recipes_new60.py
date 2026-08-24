"""
Hand-written recipes for the 9 new60 tasks that automated inference couldn't
solve (ambiguous/nonstandard delimiters, or input-order != parameter-order).
Validated the same way as infer_recipes_new60.py: compile the Reference
solution with a candidate Runner, run against sampled generated_tests, check
against the task's own evaluator_py.
"""
import json
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from infer_recipes_new60 import build_and_test_candidate, BASE, NEW60_PATH

MANUAL = {
    # "{rows} {cols}\n{row0 comma}\n{row1}\n..." -- same convention as id=3677 in the
    # original 60 (TestUtil.parseIntMatrixDimHeader already handles this).
    "3691": {
        "parse": [],
        "args": ["TestUtil.parseIntMatrixDimHeader(line)"],
        "ser": None,
    },
    # "{nums1};{nums2}" -- plain semicolon-separated pair of int arrays.
    "3397": {
        "parse": ['String[] tok = line.split(";", -1);'],
        "args": ["TestUtil.parseIntArray(tok[0])", "TestUtil.parseIntArray(tok[1])"],
        "ser": None,
    },
    # "{rows},{cols};{row0 comma chars};{row1};..." -- dims header, then char rows.
    "3492": {
        "parse": [
            'String[] tok = line.split(";", -1);',
            'char[][] grid = new char[tok.length - 1][];',
            'for (int i = 1; i < tok.length; i++) {',
            '    String[] cells = tok[i].split(",", -1);',
            '    grid[i - 1] = new char[cells.length];',
            '    for (int j = 0; j < cells.length; j++) grid[i - 1][j] = cells[j].trim().charAt(0);',
            '}',
        ],
        "args": ["grid"],
        "ser": None,
    },
    # "{rows};{cols};{row0 comma};{row1 comma};...;{row(rows-1) comma}" -- each row
    # is its own semicolon segment (NOT one flattened blob after the header).
    "3542": {
        "parse": [
            'String[] tok = line.split(";", -1);',
            'int mrows = Integer.parseInt(tok[0]);',
            'int[][] board = new int[mrows][];',
            'for (int i = 0; i < mrows; i++) board[i] = TestUtil.parseIntArray(tok[2 + i]);',
        ],
        "args": ["board"],
        "ser": None,
    },
    # "{nums};{k}" -- semicolon-separated int array + scalar.
    "3436": {
        "parse": ['String[] tok = line.split(";", -1);'],
        "args": ["TestUtil.parseIntArray(tok[0])", "Integer.parseInt(tok[1])"],
        "ser": None,
    },
    # "{operations comma-list} {k}" -- NOTE: input order is (operations, k), but the
    # method signature is kthCharacter(long k, int[] operations) -- reversed order.
    "3601": {
        "parse": ['String[] tok = line.split(" ", -1);'],
        "args": ["Long.parseLong(tok[1])", "TestUtil.parseIntArray(tok[0])"],
        "ser": None,
    },
    # "row0|row1|row2|..." -- pipe-separated rows, each comma-separated.
    "3550": {
        "parse": [
            'String[] rows = line.split("\\\\|", -1);',
            'int[][] board = new int[rows.length][];',
            'for (int i = 0; i < rows.length; i++) board[i] = TestUtil.parseIntArray(rows[i]);',
        ],
        "args": ["board"],
        "ser": None,
    },
    # "{m} {n} {horizontalCut comma};{verticalCut comma}"
    "3500": {
        "parse": [
            'String[] tok = line.split(" ", -1);',
            'String[] cuts = tok[2].split(";", -1);',
        ],
        "args": ["Integer.parseInt(tok[0])", "Integer.parseInt(tok[1])",
                  "TestUtil.parseIntArray(cuts[0])", "TestUtil.parseIntArray(cuts[1])"],
        "ser": None,
    },
    # "{k}\n{edges: row0;row1;...}" -- NOTE: input order is (k, edges), but the method
    # signature is maximizeSumOfWeights(int[][] edges, int k) -- reversed order.
    "3675": {
        "parse": ['String[] tok = line.split("\\n", -1);'],
        "args": ["TestUtil.parseIntMatrixSemi(tok[1])", "Integer.parseInt(tok[0])"],
        "ser": None,
    },
}


def main():
    with open(NEW60_PATH) as f:
        tasks = {t["id"]: t for t in json.load(f)}

    with open(os.path.join(BASE, "recipes_new60.json")) as f:
        recipes_out = json.load(f)
    with open(os.path.join(BASE, "serialization_new60.json")) as f:
        serialization_out = json.load(f)

    import re
    still_failing = []
    for tid, recipe in MANUAL.items():
        task = tasks[tid]
        m = re.search(r'public\s+[\w\[\]<>,\s]+?\s+(\w+)\s*\(', task["java_starter_code"])
        method = m.group(1)
        ok, msg = build_and_test_candidate(task, method, recipe["parse"], recipe["args"], recipe["ser"], n_cases=6)
        print(f"{tid} {task['title'][:50]:<50} -> {'OK' if ok else 'FAIL: ' + msg}")
        if ok:
            recipes_out[tid] = {"parse": recipe["parse"], "args": recipe["args"]}
            if recipe["ser"]:
                serialization_out[tid] = recipe["ser"]
        else:
            still_failing.append(tid)

    with open(os.path.join(BASE, "recipes_new60.json"), "w") as f:
        json.dump(recipes_out, f, indent=2)
    with open(os.path.join(BASE, "serialization_new60.json"), "w") as f:
        json.dump(serialization_out, f, indent=2)

    print(f"\n{len(recipes_out)}/60 total validated. Still failing: {still_failing}")


if __name__ == "__main__":
    main()
