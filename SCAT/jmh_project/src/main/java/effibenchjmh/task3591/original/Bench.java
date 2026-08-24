package effibenchjmh.task3591.original;

import effibenchjmh.common.TestUtil;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/** id=3591 | Shift Distance Between Two Strings | ORIGINAL (slow real accepted submission) vs reference in effibenchjmh.task3591.Bench */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 10, time = 1)
@Measurement(iterations = 50, time = 1)
@Fork(1)
public class Bench {

    private static final int NUM_CASES = 100;
    private List<String> lines;

    // NOTE: reuses the SAME resource case files as the reference-solution benchmark
    // (effibenchjmh.task3591.Bench) -- same task, same test cases, only the solution differs.
    private static String readCase(int i) throws IOException {
        String path = "/effibenchjmh/task3591/case" + i + ".txt";
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
            String[] tok = line.split("\n", -1);
            bh.consume(sol.shiftDistance(tok[0], tok[1], TestUtil.parseIntArray(tok[2]), TestUtil.parseIntArray(tok[3])));
        }
    }
}
