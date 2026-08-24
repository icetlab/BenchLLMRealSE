package effibenchjmh.task3753;

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id=3753 | Maximum Difference Between Even and Odd Frequency I
 * Reads ONE test case's raw input from stdin, runs it through Solution.maxDifference(),
 * and prints the result serialized to match this task's official evaluator_py.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        Solution sol = new Solution();

        var result = sol.maxDifference(line);
        System.out.println("###RESULT###");
        System.out.println(result);
    }
}
