package effibenchjmh.task3516.original;

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
 * id=3516 | Design Neighbor Sum Service | ORIGINAL (slow real accepted submission)
 * vs reference in effibenchjmh.task3516.Bench.
 *
 * Same "design"-style call-sequence format as the reference benchmark; reuses the
 * SAME resource case files (same task, same test cases, only the solution differs).
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
