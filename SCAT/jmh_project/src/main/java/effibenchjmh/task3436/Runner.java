package effibenchjmh.task3436;

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id=3436 | Find Subarray With Bitwise OR Closest to K
 * Reads ONE test case's raw input from stdin, runs it through Solution.minimumDifference(),
 * and prints the result serialized to match this task's official evaluator_py.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        Solution sol = new Solution();
        String[] tok = line.split(";", -1);
        var result = sol.minimumDifference(TestUtil.parseIntArray(tok[0]), Integer.parseInt(tok[1]));
        System.out.println("###RESULT###");
        System.out.println(result);
    }
}
