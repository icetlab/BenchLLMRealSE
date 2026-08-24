package effibenchjmh.task3221.original;

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id=3221 | Find the Peaks
 * Reads ONE test case's raw input from stdin, runs it through Solution.findPeaks(),
 * and prints the result serialized to match this task's official evaluator_py.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        Solution sol = new Solution();

        var result = sol.findPeaks(TestUtil.parseIntArray(line));
        System.out.println("###RESULT###");
        System.out.println(result.stream().map(String::valueOf).collect(java.util.stream.Collectors.joining(",")));
    }
}
