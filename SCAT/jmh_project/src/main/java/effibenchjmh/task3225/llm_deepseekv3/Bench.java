package effibenchjmh.task3225.llm_deepseekv3;

import effibenchjmh.common.TestUtil;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/** id=3225 | LLM PATCH by deepseek-v3 |  Length of Longest Subarray With at Most K Frequency | source=leetcode | difficulty=Medium */
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
        String path = "/effibenchjmh/task3225/case" + i + ".txt";
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
            String[] tok = line.split(" ", -1);
            bh.consume(sol.maxSubarrayLength(TestUtil.parseIntArray(tok[0]), Integer.parseInt(tok[1])));
        }
    }
}
