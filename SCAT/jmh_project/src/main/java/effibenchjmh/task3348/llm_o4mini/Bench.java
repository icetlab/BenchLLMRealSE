package effibenchjmh.task3348.llm_o4mini;

import effibenchjmh.common.TestUtil;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * id=3348 | Minimum Cost Walk in Weighted Graph | source=leetcode | difficulty=Hard
 *
 * Multi-section stdin-judge-style format, unique to this task:
 *   line 0: n
 *   line 1: numEdges
 *   next numEdges lines: "u v w" (space-separated edge triple)
 *   next line: numQueries
 *   next numQueries lines: "a b" (space-separated query pair)
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 10, time = 1)
@Measurement(iterations = 50, time = 1)
@Fork(1)
public class Bench {

    private static final int NUM_CASES = 100;
    private List<String> lines;

    private static String readCase(int i) throws IOException {
        String path = "/effibenchjmh/task3348/case" + i + ".txt";
        try (InputStream in = Bench.class.getResourceAsStream(path)) {
            if (in == null) throw new IOException("Missing resource: " + path);
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    @Setup(Level.Trial)
    public void setup() throws Exception {
        lines = new ArrayList<>();
        for (int i = 0; i < NUM_CASES; i++) lines.add(readCase(i));
    }

    @Benchmark
    public void runAllCases(Blackhole bh) {
        Solution sol = new Solution();
        for (String line : lines) {
            String[] rows = line.split("\n", -1);
            int idx = 0;
            int n = Integer.parseInt(rows[idx++].trim());
            int numEdges = Integer.parseInt(rows[idx++].trim());
            int[][] edges = new int[numEdges][];
            for (int i = 0; i < numEdges; i++) edges[i] = TestUtil.parseIntArraySpaceSep(rows[idx++]);
            int numQueries = Integer.parseInt(rows[idx++].trim());
            int[][] query = new int[numQueries][];
            for (int i = 0; i < numQueries; i++) query[i] = TestUtil.parseIntArraySpaceSep(rows[idx++]);

            bh.consume(sol.minimumCost(n, edges, query));
        }
    }
}
