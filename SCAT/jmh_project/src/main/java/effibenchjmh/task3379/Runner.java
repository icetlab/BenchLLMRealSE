package effibenchjmh.task3379;

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id=3379 | Score of a String
 * Reads ONE test case's raw input from stdin, runs it through Solution.scoreOfString(),
 * and prints the result serialized to match this task's official evaluator_py.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        Solution sol = new Solution();

        var result = sol.scoreOfString(line);
        System.out.println("###RESULT###");
        System.out.println(result);
    }
}
