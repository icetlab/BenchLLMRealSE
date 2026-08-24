"""
Generates a Runner.java companion class for each of the 58 standard tasks
(everything except the 2 SPECIAL_TASKS, which get a hand-written Runner.java).

Runner.java reads ONE test case's raw input string from stdin, parses it with the
exact same recipe used by that task's JMH Bench.java, invokes the Solution method,
and prints the result to stdout serialized in the exact format that task's official
EffiBench-X evaluator_py expects (plain value for scalars; comma or newline-joined
for array/list returns, per the SERIALIZATION table). This lets the Python-side
verifier reuse the *actual* dataset evaluator function for correctness checking,
rather than reimplementing comparison logic ourselves.

Runner.java is written into BOTH the reference package (effibenchjmh.task{id})
and, where present, the .original package -- so either solution variant, or a
copy with an LLM patch applied, can be compiled and tested identically.
"""
import json, re, os, sys

BASE = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
JAVA_ROOT = os.path.join(BASE, "jmh_project", "src", "main", "java", "effibenchjmh")

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from recipes import RECIPES, SERIALIZATION, SPECIAL_TASKS

with open(os.path.join(BASE, "effibenchx_final60.json")) as f:
    tasks = json.load(f)
by_id = {t["id"]: t for t in tasks}


def method_name(starter_code):
    m = re.search(r'public\s+[\w\[\]<>,\s]+?\s+(\w+)\s*\(', starter_code)
    return m.group(1)


def return_type(starter_code):
    m = re.search(r'public\s+([\w\[\]<>,\s]+?)\s+\w+\s*\(', starter_code)
    return m.group(1).strip() if m else None


def print_statement(tid, rtype):
    """Java statement that prints `result` in the format this task's evaluator expects."""
    ser = SERIALIZATION.get(tid)
    if ser == "int[]:comma":
        return 'System.out.println(java.util.stream.IntStream.of(result).mapToObj(String::valueOf).collect(java.util.stream.Collectors.joining(",")));'
    if ser == "int[]:newline":
        return 'System.out.println(java.util.stream.IntStream.of(result).mapToObj(String::valueOf).collect(java.util.stream.Collectors.joining("\\n")));'
    if ser == "long[]:comma":
        return 'System.out.println(java.util.stream.LongStream.of(result).mapToObj(String::valueOf).collect(java.util.stream.Collectors.joining(",")));'
    if ser == "List<Integer>:comma":
        return 'System.out.println(result.stream().map(String::valueOf).collect(java.util.stream.Collectors.joining(",")));'
    if ser == "List<String>:comma":
        return 'System.out.println(String.join(",", result));'
    # Default: scalar (int/long/boolean/String) -- Java's toString() already matches
    # what these tasks' evaluators expect (plain value, or lowercase true/false).
    return 'System.out.println(result);'


RUNNER_TEMPLATE = '''package effibenchjmh.task{id}{suffix};

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id={id} | {title}
 * Reads ONE test case's raw input from stdin, runs it through Solution.{method}(),
 * and prints the result serialized to match this task's official evaluator_py.
 * Used for correctness verification (not performance measurement -- see Bench.java
 * in this package for the JMH benchmark).
 *
 * NOTE: reads stdin via readAllBytes(), NOT line-by-line via BufferedReader.readLine().
 * readLine()-based loops silently drop a trailing empty line before EOF (there is no
 * way to distinguish "no more input" from "one final empty line" that way), which
 * breaks tasks whose format encodes an empty trailing segment (e.g. an empty array
 * parameter). readAllBytes() preserves the exact input bytes, matching how the JMH
 * benchmark reads its resource case files.
 */
public class Runner {{
    public static void main(String[] args) throws Exception {{
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        Solution sol = new Solution();
{parse_block}
        var result = sol.{method}({args_joined});
        System.out.println("###RESULT###");
        {print_stmt}
    }}
}}
'''

count = 0
for tid, recipe in RECIPES.items():
    if tid in SPECIAL_TASKS:
        continue
    t = by_id[tid]
    sc = t["java_starter_code"]
    mname = method_name(sc)
    rtype = return_type(sc)
    parse_block = "\n".join("        " + l for l in recipe["parse"])
    args_joined = ", ".join(recipe["args"])
    print_stmt = print_statement(tid, rtype)

    for suffix in ["", ".original"]:
        pkg_dir = os.path.join(JAVA_ROOT, f"task{tid}", *(["original"] if suffix else []))
        if not os.path.isdir(pkg_dir):
            continue  # some tasks may not have an .original variant (already filtered to 60 that do, but guard anyway)
        code = RUNNER_TEMPLATE.format(
            id=tid, suffix=suffix, title=t["title"], method=mname,
            parse_block=parse_block, args_joined=args_joined, print_stmt=print_stmt,
        )
        with open(os.path.join(pkg_dir, "Runner.java"), "w") as f:
            f.write(code)
    count += 1

print(f"Generated Runner.java for {count} standard tasks (reference + original variants)")
print(f"Skipped {len(SPECIAL_TASKS)} special tasks: {SPECIAL_TASKS} (need hand-written Runner.java)")
