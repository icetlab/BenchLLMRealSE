"""
Master input-parsing recipes for all 120 final EffiBench-X Java tasks (the
original 60 plus a second new60 batch sampled to double the SCAT pilot's
sample size). The original 60's recipes came from the batch1/batch2/
replacement18 generator scripts used to build the JMH benchmarks; the new60
batch was inferred automatically (infer_recipes_new60.py) and validated
end-to-end against each task's Reference solution and real evaluator_py, with
9 tasks needing hand-written recipes (manual_recipes_new60.py) for
nonstandard delimiters or input-order-vs-parameter-order mismatches. Each
recipe describes how to turn one raw generated_tests[].input string into the
Java arguments for that task's method.

"parse": list of Java statements to run before building the argument list
         (usually splitting `line` into `tok[]`).
"args":  Java expressions, in method-parameter order, referencing `line` or `tok`.

Two tasks (3516, 3348), both from the original 60, are NOT simple
single-method calls and are excluded here; they are handled by hand-written
Runner.java / Bench.java (design-style call sequence, and multi-section
stdin format, respectively).
"""

RECIPES = {
    "3684": {"parse": ['String[] tok = line.split(" ", -1);'], "args": ["tok[0]", "tok[1]"]},
    "3263": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3199": {"parse": ['String[] tok = line.split(" ", -1);'], "args": ["Integer.parseInt(tok[0])", "Integer.parseInt(tok[1])"]},
    "3372": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3361": {"parse": [], "args": ["line"]},
    "3311": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3252": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3708": {"parse": [], "args": ["TestUtil.parseIntMatrixSemi(line)"]},
    "3612": {"parse": ['String[] tok = line.split(" ", -1);'], "args": ["TestUtil.parseIntList(tok[0])", "Integer.parseInt(tok[1])"]},
    "3244": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3677": {"parse": [], "args": ["TestUtil.parseIntMatrixDimHeader(line)"]},
    "3510": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3210": {"parse": ['String[] tok = line.split(" ", -1);'], "args": ["tok[0]", "Integer.parseInt(tok[1])"]},
    "3209": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3245": {"parse": ['String[] tok = line.split(";", -1);'], "args": ["tok[0]", "tok[1]", "tok[2]", "Integer.parseInt(tok[3])"]},
    "3360": {"parse": ['String[] tok = line.split(" ", -1);'], "args": ["tok[0]", "Integer.parseInt(tok[1])"]},
    "3380": {"parse": ['String[] tok = line.split(" ", -1);'], "args": ["TestUtil.parseIntArray(tok[0])", "Integer.parseInt(tok[1])"]},
    "3591": {"parse": ['String[] tok = line.split("\\n", -1);'], "args": ["tok[0]", "tok[1]", "TestUtil.parseIntArray(tok[2])", "TestUtil.parseIntArray(tok[3])"]},
    "3712": {"parse": ['String[] tok = line.split("\\\\|", -1);'], "args": ["TestUtil.parseIntArray(tok[0])", "TestUtil.parseIntArray(tok[1])", "Long.parseLong(tok[2])"]},
    "3211": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3648": {"parse": [], "args": ["TestUtil.parseIntMatrixCountPrefix(line)"]},
    "3350": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3640": {"parse": ['String[] tok = line.split(" ", -1);'], "args": ["TestUtil.parseIntArray(tok[0])", "Integer.parseInt(tok[1])", "Integer.parseInt(tok[2])"]},
    "3548": {"parse": ['String[] tok = line.split(" ", -1);'], "args": ["Integer.parseInt(tok[0])", "Integer.parseInt(tok[1])"]},
    "3375": {"parse": ['String[] tok = line.split(" ", -1);'], "args": ["TestUtil.parseIntArray(tok[0])", "Integer.parseInt(tok[1])"]},
    "3562": {"parse": [], "args": ["TestUtil.parseIntListMatrix(line)"]},
    "3423": {"parse": ['String[] tok = line.split(" ", -1);'], "args": ["TestUtil.parseIntArray(tok[0])", "TestUtil.parseIntMatrixSemi(tok[1])"]},
    "3197": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3316": {"parse": ['String[] tok = line.split(" ", -1);'], "args": ["TestUtil.parseIntArray(tok[0])", "Integer.parseInt(tok[1])"]},
    "3555": {"parse": ['String[] tok = line.split(" ", -1);'], "args": ["TestUtil.parseIntArray(tok[0])", "Integer.parseInt(tok[1])", "Integer.parseInt(tok[2])"]},
    "3321": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3207": {"parse": ['String[] tok = line.split(",", -1);'], "args": ["tok[0]", "tok[1]", "tok[2]"]},
    "3723": {"parse": ['String[] tok = line.split(" ", -1);'], "args": ["TestUtil.parseIntArray(tok[0])", "Integer.parseInt(tok[1])"]},
    "3519": {"parse": ['String[] tok = line.split("\\n", -1);'], "args": ["Integer.parseInt(tok[0])", "TestUtil.parseIntMatrixSemi(tok[1])"]},
    "3469": {"parse": ['String[] tok = line.split(",", -1);'], "args": ["Integer.parseInt(tok[0])", "Integer.parseInt(tok[1])"]},
    "3429": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3353": {"parse": [], "args": ["line"]},
    "3625": {"parse": [], "args": ["Integer.parseInt(line)"]},
    "3611": {"parse": [], "args": ["TestUtil.parseIntList(line)"]},
    "3202": {"parse": [], "args": ["TestUtil.parseStringListMatrix(line)"]},
    "3579": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3325": {"parse": ['String[] tok = line.split("\\n", -1);'], "args": ["TestUtil.parseIntMatrixSemi(tok[0])", "TestUtil.parseIntMatrixSemi(tok[1])"]},
    "3318": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3737": {"parse": ['int[][] costMatrix = TestUtil.parseIntMatrixRowCountHeader(line);'], "args": ["costMatrix.length", "costMatrix"]},
    "3584": {"parse": ['String[] tok = line.split(" ", -1);'], "args": ["tok[0]", "tok[1]"]},
    "3278": {"parse": [], "args": ["TestUtil.parseIntMatrixSemi(line)"]},
    "3291": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3620": {"parse": ['String[] tok = line.split(" ", -1);'], "args": ["TestUtil.parseIntArray(tok[0])", "Integer.parseInt(tok[1])"]},
    "3327": {"parse": ['String[] tok = line.split(" ", -1);'], "args": ["TestUtil.parseIntArray(tok[0])", "Integer.parseInt(tok[1])", "Integer.parseInt(tok[2])"]},
    "3364": {"parse": ['String[] tok = line.split(" ", -1);'], "args": ["TestUtil.parseIntArray(tok[0])", "TestUtil.parseIntArray(tok[1])"]},
    "3532": {"parse": [], "args": ["TestUtil.parseIntMatrixSemi(line)"]},
    "3305": {"parse": [], "args": ["TestUtil.parseStringArray(line)"]},
    "3431": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3414": {"parse": [], "args": ["Integer.parseInt(line)"]},
    "3618": {"parse": ['String[] tok = line.split(" ", -1);'], "args": ["tok[0]", "Integer.parseInt(tok[1])"]},
    "3238": {"parse": ['String[] tok = line.split("\\n", -1);'], "args": ["tok[0]", "tok[1]", "TestUtil.parseStringArray(tok[2])", "TestUtil.parseStringArray(tok[3])", "TestUtil.parseIntArray(tok[4])"]},
    "3630": {"parse": ['String[] tok = line.split(" ", -1);'], "args": ["tok[0]", "Integer.parseInt(tok[1])", "TestUtil.parseIntList(tok[2])"]},
    "3312": {"parse": [], "args": ["line"]},
    "3446": {"parse": ['String[] tok = line.split("\\\\|", -1);'], "args": ["TestUtil.parseIntArray(tok[0])", "TestUtil.parseIntArray(tok[1])", "Integer.parseInt(tok[2])"]},
    "3553": {"parse": ['String[] tok = line.split(" ", -1);'], "args": ["tok[0]", "tok[1]"]},
    "3543": {"parse": ['String[] tok = line.split(" ", -1);'], "args": ["tok[0]", "Integer.parseInt(tok[1])"]},
    "3731": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3768": {"parse": [], "args": ["line"]},
    "3225": {"parse": ['String[] tok = line.split(" ", -1);'], "args": ["TestUtil.parseIntArray(tok[0])", "Integer.parseInt(tok[1])"]},
    "3534": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3477": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3643": {"parse": ['String[] tok = line.split(" ", -1);'], "args": ["TestUtil.parseIntArray(tok[0])", "TestUtil.parseIntMatrixSemi(tok[1])"]},
    "3218": {"parse": ['String[] tok = line.split("\\\\|", -1);'], "args": ["TestUtil.parseIntMatrixSemi(tok[1])", "TestUtil.parseIntArray(tok[2])"]},
    "3588": {"parse": [], "args": ["line"]},
    "3248": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3345": {"parse": ['String[] tok = line.split(" ", -1);'], "args": ["TestUtil.parseIntArray(tok[0])", "Integer.parseInt(tok[1])"]},
    "3298": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3536": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3313": {"parse": ['String[] tok = line.split(" ", -1);'], "args": ["TestUtil.parseIntArray(tok[0])", "Integer.parseInt(tok[1])"]},
    "3344": {"parse": [], "args": ["TestUtil.parseIntMatrixSemi(line)"]},

    # --- new60 batch (added to double the SCAT pilot sample size) ---
    "3194": {"parse": ["String[] tok = line.split(\" \", -1);"], "args": ["TestUtil.parseStringArray(tok[0])", "tok[1].trim().charAt(0)"]},
    "3201": {"parse": ["String[] tok = line.split(\",\", -1);"], "args": ["Integer.parseInt(tok[0])", "Integer.parseInt(tok[1])"]},
    "3220": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3221": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3226": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3243": {"parse": ["String[] tok = line.split(\",\", -1);"], "args": ["Long.parseLong(tok[0])", "Long.parseLong(tok[1])", "Integer.parseInt(tok[2])", "tok[3]"]},
    "3267": {"parse": [], "args": ["line"]},
    "3289": {"parse": ["String[] tok = line.split(\" \", -1);"], "args": ["TestUtil.parseIntArray(tok[0])", "TestUtil.parseIntArray(tok[1])"]},
    "3310": {"parse": ["String[] tok = line.split(\",\", -1);"], "args": ["Integer.parseInt(tok[0])", "Integer.parseInt(tok[1])", "Integer.parseInt(tok[2])"]},
    "3334": {"parse": ["String[] tok = line.split(\" \", -1);"], "args": ["TestUtil.parseIntArray(tok[0])", "TestUtil.parseIntArray(tok[1])"]},
    "3335": {"parse": [], "args": ["TestUtil.parseIntMatrixSemi(line)"]},
    "3355": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3363": {"parse": ["String[] tok = line.split(\" \", -1);"], "args": ["TestUtil.parseIntArray(tok[0])", "TestUtil.parseIntArray(tok[1])"]},
    "3379": {"parse": [], "args": ["line"]},
    "3381": {"parse": ["String[] tok = line.split(\" \", -1);"], "args": ["TestUtil.parseIntArray(tok[0])", "Integer.parseInt(tok[1])"]},
    "3382": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3383": {"parse": ["String[] tok = line.split(\" \", -1);"], "args": ["TestUtil.parseIntArray(tok[0])", "Integer.parseInt(tok[1])"]},
    "3384": {"parse": ["String[] tok = line.split(\" \", -1);"], "args": ["tok[0]", "Integer.parseInt(tok[1])"]},
    "3397": {"parse": ["String[] tok = line.split(\";\", -1);"], "args": ["TestUtil.parseIntArray(tok[0])", "TestUtil.parseIntArray(tok[1])"]},
    "3405": {"parse": [], "args": ["line"]},
    "3421": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3436": {"parse": ["String[] tok = line.split(\";\", -1);"], "args": ["TestUtil.parseIntArray(tok[0])", "Integer.parseInt(tok[1])"]},
    "3442": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3443": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3454": {"parse": ["String[] tok = line.split(\" \", -1);"], "args": ["TestUtil.parseIntArray(tok[0])", "TestUtil.parseIntArray(tok[1])"]},
    "3463": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3468": {"parse": ["String[] tok = line.split(\" \", -1);"], "args": ["tok[0]", "Integer.parseInt(tok[1])"]},
    "3492": {"parse": ["String[] tok = line.split(\";\", -1);", "char[][] grid = new char[tok.length - 1][];", "for (int i = 1; i < tok.length; i++) {", "    String[] cells = tok[i].split(\",\", -1);", "    grid[i - 1] = new char[cells.length];", "    for (int j = 0; j < cells.length; j++) grid[i - 1][j] = cells[j].trim().charAt(0);", "}"], "args": ["grid"]},
    "3500": {"parse": ["String[] tok = line.split(\" \", -1);", "String[] cuts = tok[2].split(\";\", -1);"], "args": ["Integer.parseInt(tok[0])", "Integer.parseInt(tok[1])", "TestUtil.parseIntArray(cuts[0])", "TestUtil.parseIntArray(cuts[1])"]},
    "3522": {"parse": ["String[] tok = line.split(\" \", -1);"], "args": ["TestUtil.parseIntArray(tok[0])", "Integer.parseInt(tok[1])"]},
    "3523": {"parse": ["String[] tok = line.split(\" \", -1);"], "args": ["TestUtil.parseIntArray(tok[0])", "Integer.parseInt(tok[1])"]},
    "3542": {"parse": ["String[] tok = line.split(\";\", -1);", "int mrows = Integer.parseInt(tok[0]);", "int[][] board = new int[mrows][];", "for (int i = 0; i < mrows; i++) board[i] = TestUtil.parseIntArray(tok[2 + i]);"], "args": ["board"]},
    "3550": {"parse": ["String[] rows = line.split(\"\\\\|\", -1);", "int[][] board = new int[rows.length][];", "for (int i = 0; i < rows.length; i++) board[i] = TestUtil.parseIntArray(rows[i]);"], "args": ["board"]},
    "3557": {"parse": ["String[] tok = line.split(\" \", -1);"], "args": ["TestUtil.parseStringArray(tok[0])", "tok[1]"]},
    "3563": {"parse": [], "args": ["TestUtil.parseIntListMatrix(line)"]},
    "3568": {"parse": ["String[] tok = line.split(\",\", -1);"], "args": ["Integer.parseInt(tok[0])", "Integer.parseInt(tok[1])", "Integer.parseInt(tok[2])"]},
    "3570": {"parse": ["String[] tok = line.split(\" \", -1);"], "args": ["tok[0]", "Integer.parseInt(tok[1])"]},
    "3576": {"parse": ["String[] tok = line.split(\" \", -1);"], "args": ["TestUtil.parseIntArray(tok[0])", "tok[1]"]},
    "3594": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3595": {"parse": ["String[] tok = line.split(\" \", -1);"], "args": ["tok[0]", "tok[1]", "Integer.parseInt(tok[2])"]},
    "3601": {"parse": ["String[] tok = line.split(\" \", -1);"], "args": ["Long.parseLong(tok[1])", "TestUtil.parseIntArray(tok[0])"]},
    "3604": {"parse": ["String[] tok = line.split(\" \", -1);"], "args": ["Integer.parseInt(tok[0])", "Integer.parseInt(tok[1])", "Integer.parseInt(tok[2])"]},
    "3605": {"parse": [], "args": ["TestUtil.parseIntList(line)"]},
    "3619": {"parse": [], "args": ["TestUtil.parseIntList(line)"]},
    "3622": {"parse": ["String[] tok = line.split(\" \", -1);"], "args": ["TestUtil.parseIntArray(tok[0])", "Integer.parseInt(tok[1])", "Integer.parseInt(tok[2])"]},
    "3630": {"parse": ["String[] tok = line.split(\" \", -1);"], "args": ["tok[0]", "Integer.parseInt(tok[1])", "TestUtil.parseIntList(tok[2])"]},
    "3644": {"parse": ["String[] tok = line.split(\" \", -1);"], "args": ["TestUtil.parseIntList(tok[0])", "Integer.parseInt(tok[1])", "Integer.parseInt(tok[2])"]},
    "3649": {"parse": ["String[] tok = line.split(\" \", -1);"], "args": ["TestUtil.parseIntList(tok[0])", "Integer.parseInt(tok[1])"]},
    "3654": {"parse": ["String[] tok = line.split(\" \", -1);"], "args": ["TestUtil.parseIntArray(tok[0])", "Integer.parseInt(tok[1])", "Integer.parseInt(tok[2])", "Integer.parseInt(tok[3])"]},
    "3675": {"parse": ["String[] tok = line.split(\"\\n\", -1);"], "args": ["TestUtil.parseIntMatrixSemi(tok[1])", "Integer.parseInt(tok[0])"]},
    "3685": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3687": {"parse": ["String[] tok = line.split(\" \", -1);"], "args": ["TestUtil.parseIntMatrixSemi(tok[0])", "TestUtil.parseIntArray(tok[1])"]},
    "3691": {"parse": [], "args": ["TestUtil.parseIntMatrixDimHeader(line)"]},
    "3696": {"parse": [], "args": ["line"]},
    "3702": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3709": {"parse": ["String[] tok = line.split(\" \", -1);"], "args": ["tok[0]", "Integer.parseInt(tok[1])"]},
    "3733": {"parse": [], "args": ["TestUtil.parseIntMatrixSemi(line)"]},
    "3739": {"parse": ["String[] tok = line.split(\",\", -1);"], "args": ["Integer.parseInt(tok[0])", "Integer.parseInt(tok[1])", "Integer.parseInt(tok[2])"]},
    "3747": {"parse": [], "args": ["TestUtil.parseIntArray(line)"]},
    "3753": {"parse": [], "args": ["line"]},
}

# Output-serialization convention, verified per task against its actual evaluator_py
# (see conversation history for the raw evaluator inspection). "scalar" covers
# int/long/boolean/String returns, where Java's default toString() already matches
# what the evaluator expects (plain value, or "true"/"false" lowercase).
SERIALIZATION = {
    # comma-separated array/list returns (the vast majority)
    "3350": "int[]:comma", "3532": "int[]:comma", "3555": "int[]:comma",
    "3562": "int[]:comma", "3611": "int[]:comma", "3218": "long[]:comma",
    "3245": "List<Integer>:comma", "3708": "List<Integer>:comma",
    "3202": "List<String>:comma",
    # the one outlier: newline-separated
    "3348": "int[]:newline",
    # --- new60 batch ---
    "3194": "List<Integer>:comma",
    "3221": "List<Integer>:comma",
    "3226": "int[]:comma",
    "3310": "long[]:comma",
    "3363": "long[]:comma",
    "3522": "int[]:comma",
    "3523": "int[]:comma",
    "3576": "int[]:comma",
    "3605": "int[]:comma",
    "3687": "int[]:comma",
}

SPECIAL_TASKS = {"3516", "3348"}  # handled by hand-written Runner/Bench, not the generic template

# RECIPES above still contains leftover entries for tasks that were later
# excluded during sampling (no valid Original baseline, ratio < 1.2x) and
# replaced -- filter down to exactly the final 120 tasks (118 standard + 2
# special) so callers never accidentally process a stale/removed task.
import json as _json
import os as _os

_FINAL120_PATH = _os.path.join(_os.path.dirname(_os.path.dirname(_os.path.abspath(__file__))), "effibenchx_final120.json")
with open(_FINAL120_PATH) as _f:
    _FINAL_IDS = {t["id"] for t in _json.load(_f)}

RECIPES = {tid: r for tid, r in RECIPES.items() if tid in _FINAL_IDS}
