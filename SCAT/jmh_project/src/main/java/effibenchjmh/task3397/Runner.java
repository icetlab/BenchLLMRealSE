package effibenchjmh.task3397;

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id=3397 | Find the Integer Added to Array I
 * Reads ONE test case's raw input from stdin, runs it through Solution.addedInteger(),
 * and prints the result serialized to match this task's official evaluator_py.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        Solution sol = new Solution();
        String[] tok = line.split(";", -1);
        var result = sol.addedInteger(TestUtil.parseIntArray(tok[0]), TestUtil.parseIntArray(tok[1]));
        System.out.println("###RESULT###");
        System.out.println(result);
    }
}
