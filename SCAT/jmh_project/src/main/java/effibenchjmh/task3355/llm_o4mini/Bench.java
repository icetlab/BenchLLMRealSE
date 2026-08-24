package effibenchjmh.task3355.llm_o4mini;

import effibenchjmh.common.TestUtil;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/** id=3355 | LLM PATCH by o4-mini |  Minimum Levels to Gain More Points | source=leetcode | difficulty=Medium */
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
        String path = "/effibenchjmh/task3355/case" + i + ".txt";
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

            bh.consume(sol.minimumLevels(TestUtil.parseIntArray(line)));
        }
    }
}
