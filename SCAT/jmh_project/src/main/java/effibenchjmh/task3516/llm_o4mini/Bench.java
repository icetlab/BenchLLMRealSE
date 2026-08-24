package effibenchjmh.task3516.llm_o4mini;

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
 * id=3516 | Design Neighbor Sum Service | source=leetcode | difficulty=Easy
 *
 * "Design"-style LeetCode problem: input is a sequence of method calls on one
 * NeighborSum instance, not a single function call.
 * Format: "NeighborSum,adjacentSum,adjacentSum,...\n{grid ;/,-matrix} | {arg1} | {arg2} | ..."
 * The first name is always the constructor; the grid is the constructor argument.
 * Each subsequent name/arg pair is a call to adjacentSum(int) or diagonalSum(int).
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
        String path = "/effibenchjmh/task3516/case" + i + ".txt";
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
        for (String line : lines) {
            String[] top = line.split("\n", -1);
            String[] calls = top[0].split(",", -1);
            String[] args = top[1].split("\\|", -1);
            for (int i = 0; i < args.length; i++) args[i] = args[i].trim();

            // args[0] is always the constructor's grid argument.
            NeighborSum obj = new NeighborSum(TestUtil.parseIntMatrixSemi(args[0]));
            for (int i = 1; i < calls.length; i++) {
                int value = Integer.parseInt(args[i]);
                switch (calls[i]) {
                    case "adjacentSum":
                        bh.consume(obj.adjacentSum(value));
                        break;
                    case "diagonalSum":
                        bh.consume(obj.diagonalSum(value));
                        break;
                    default:
                        throw new IllegalStateException("Unknown method call: " + calls[i]);
                }
            }
        }
    }
}
