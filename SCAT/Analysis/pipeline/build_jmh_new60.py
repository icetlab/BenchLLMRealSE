"""
Materializes the full jmh_project directory structure for the new60 batch:
Solution.java + Bench.java for both Original and Reference variants, plus the
100-case resource files, mirroring the layout of the original 60 tasks. Then
generates Runner.java for correctness verification (same template as
gen_runners.py, generalized to the merged 120-task recipes).

Run after recipes.py has been updated with the new60 entries (already done).
"""
import json
import os
import re
import sys

BASE = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
JAVA_ROOT = os.path.join(BASE, "jmh_project", "src", "main", "java", "effibenchjmh")
RESOURCES_ROOT = os.path.join(BASE, "jmh_project", "src", "main", "resources", "effibenchjmh")

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from recipes import RECIPES, SERIALIZATION, SPECIAL_TASKS

with open(os.path.join(BASE, "effibenchx_new60.json")) as f:
    NEW_TASKS = json.load(f)


def method_name(starter_code):
    m = re.search(r'public\s+[\w\[\]<>,\s]+?\s+(\w+)\s*\(', starter_code)
    return m.group(1)


def return_type(starter_code):
    m = re.search(r'public\s+([\w\[\]<>,\s]+?)\s+\w+\s*\(', starter_code)
    return m.group(1).strip() if m else None


def write_solution(pkg_dir, pkg_name, solution_src):
    os.makedirs(pkg_dir, exist_ok=True)
    pair_import = "" if "class Pair" in solution_src else "import effibenchjmh.common.Pair;\n"
    header = (
        f"package effibenchjmh.{pkg_name};\n\n"
        "import java.util.*;\n"
        "import java.util.stream.*;\n"
        "import java.util.concurrent.*;\n"
        "import java.util.concurrent.atomic.*;\n"
        "import java.math.*;\n"
        f"{pair_import}\n"
    )
    body = solution_src
    lines = body.splitlines()
    if lines and lines[0].strip().startswith("package "):
        body = "\n".join(lines[1:])
    with open(os.path.join(pkg_dir, "Solution.java"), "w", encoding="utf-8") as f:
        f.write(header + body + "\n")


BENCH_TEMPLATE = '''package effibenchjmh.{pkg_name};

import effibenchjmh.common.TestUtil;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/** id={id} | {title_comment}*/
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 10, time = 1)
@Measurement(iterations = 50, time = 1)
@Fork(1)
public class Bench {{

    private static final int NUM_CASES = {num_cases};
    private List<String> lines;

    private static String readCase(int i) throws IOException {{
        String path = "/effibenchjmh/task{id}/case" + i + ".txt";
        try (InputStream in = Bench.class.getResourceAsStream(path)) {{
            if (in == null) throw new IOException("Missing resource: " + path);
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }}
    }}

    @Setup(Level.Trial)
    public void setup() throws Exception {{
        lines = new ArrayList<>();
        for (int i = 0; i < NUM_CASES; i++) lines.add(readCase(i));
    }}

    @Benchmark
    public void runAllCases(Blackhole bh) {{
        Solution sol = new Solution();
        for (String line : lines) {{
{parse_block}
            bh.consume(sol.{method}({args_joined}));
        }}
    }}
}}
'''

RUNNER_TEMPLATE = '''package effibenchjmh.task{id}{suffix};

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id={id} | {title}
 * Reads ONE test case's raw input from stdin, runs it through Solution.{method}(),
 * and prints the result serialized to match this task's official evaluator_py.
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


def print_statement(tid, rtype):
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
    return 'System.out.println(result);'


def main():
    count = 0
    for task in NEW_TASKS:
        tid = task["id"]
        if tid in SPECIAL_TASKS:
            print(f"  [skip] {tid} is a SPECIAL_TASK, needs hand-written files")
            continue
        recipe = RECIPES.get(tid)
        if recipe is None:
            print(f"  [warn] {tid} has no recipe, skipping")
            continue

        sc = task["java_starter_code"]
        mname = method_name(sc)
        rtype = return_type(sc)
        tests = json.loads(task["generated_tests"]) if isinstance(task["generated_tests"], str) else task["generated_tests"]
        num_cases = len(tests)

        args_joined = ", ".join(recipe["args"])
        print_stmt = print_statement(tid, rtype)

        for suffix, pkg_suffix, sol_src in [
            ("", "", task["java_reference_solution"]),
            (".original", ".original", task["java_original_solution"]),
        ]:
            pkg_dir = os.path.join(JAVA_ROOT, f"task{tid}", *(["original"] if suffix else []))
            pkg_name = f"task{tid}{pkg_suffix}"

            write_solution(pkg_dir, pkg_name, sol_src)

            bench_parse_block = "\n".join("            " + l for l in recipe["parse"])
            bench_code = BENCH_TEMPLATE.format(
                pkg_name=pkg_name, id=tid,
                title_comment=(f"{task['title']} | source={task['source']} | difficulty={task['difficulty']} " if suffix == "" else
                                f"{task['title']} | ORIGINAL (slow real accepted submission) vs reference in effibenchjmh.task{tid}.Bench "),
                num_cases=num_cases, parse_block=bench_parse_block, method=mname, args_joined=args_joined,
            )
            with open(os.path.join(pkg_dir, "Bench.java"), "w", encoding="utf-8") as f:
                f.write(bench_code)

            runner_parse_block = "\n".join("        " + l for l in recipe["parse"])
            runner_code = RUNNER_TEMPLATE.format(
                id=tid, suffix=suffix, title=task["title"], method=mname,
                parse_block=runner_parse_block, args_joined=args_joined, print_stmt=print_stmt,
            )
            with open(os.path.join(pkg_dir, "Runner.java"), "w", encoding="utf-8") as f:
                f.write(runner_code)

        resource_dir = os.path.join(RESOURCES_ROOT, f"task{tid}")
        os.makedirs(resource_dir, exist_ok=True)
        for i, case in enumerate(tests):
            with open(os.path.join(resource_dir, f"case{i}.txt"), "w", encoding="utf-8") as f:
                f.write(case["input"])

        count += 1

    print(f"\nBuilt jmh_project files for {count} new60 tasks")


if __name__ == "__main__":
    main()
