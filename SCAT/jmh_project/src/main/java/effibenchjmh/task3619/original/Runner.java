package effibenchjmh.task3619.original;

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id=3619 | Adjacent Increasing Subarrays Detection II
 * Reads ONE test case's raw input from stdin, runs it through Solution.maxIncreasingSubarrays(),
 * and prints the result serialized to match this task's official evaluator_py.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        Solution sol = new Solution();

        var result = sol.maxIncreasingSubarrays(TestUtil.parseIntList(line));
        System.out.println("###RESULT###");
        System.out.println(result);
    }
}
