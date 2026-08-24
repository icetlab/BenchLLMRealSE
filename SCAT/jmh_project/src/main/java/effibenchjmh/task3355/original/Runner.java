package effibenchjmh.task3355.original;

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id=3355 | Minimum Levels to Gain More Points
 * Reads ONE test case's raw input from stdin, runs it through Solution.minimumLevels(),
 * and prints the result serialized to match this task's official evaluator_py.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        Solution sol = new Solution();

        var result = sol.minimumLevels(TestUtil.parseIntArray(line));
        System.out.println("###RESULT###");
        System.out.println(result);
    }
}
