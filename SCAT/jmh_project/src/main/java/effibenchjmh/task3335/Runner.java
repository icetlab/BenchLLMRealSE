package effibenchjmh.task3335;

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id=3335 | Minimum Operations to Write the Letter Y on a Grid
 * Reads ONE test case's raw input from stdin, runs it through Solution.minimumOperationsToWriteY(),
 * and prints the result serialized to match this task's official evaluator_py.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        Solution sol = new Solution();

        var result = sol.minimumOperationsToWriteY(TestUtil.parseIntMatrixSemi(line));
        System.out.println("###RESULT###");
        System.out.println(result);
    }
}
