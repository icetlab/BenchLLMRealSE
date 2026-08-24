package effibenchjmh.task3563.original;

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id=3563 | Select Cells in Grid With Maximum Score
 * Reads ONE test case's raw input from stdin, runs it through Solution.maxScore(),
 * and prints the result serialized to match this task's official evaluator_py.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        Solution sol = new Solution();

        var result = sol.maxScore(TestUtil.parseIntListMatrix(line));
        System.out.println("###RESULT###");
        System.out.println(result);
    }
}
