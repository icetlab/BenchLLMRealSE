package effibenchjmh.task3675;

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id=3675 | Maximize Sum of Weights after Edge Removals
 * Reads ONE test case's raw input from stdin, runs it through Solution.maximizeSumOfWeights(),
 * and prints the result serialized to match this task's official evaluator_py.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        Solution sol = new Solution();
        String[] tok = line.split("\n", -1);
        var result = sol.maximizeSumOfWeights(TestUtil.parseIntMatrixSemi(tok[1]), Integer.parseInt(tok[0]));
        System.out.println("###RESULT###");
        System.out.println(result);
    }
}
