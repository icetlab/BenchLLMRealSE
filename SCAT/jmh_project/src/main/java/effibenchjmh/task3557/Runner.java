package effibenchjmh.task3557;

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id=3557 | Minimum Number of Valid Strings to Form Target II
 * Reads ONE test case's raw input from stdin, runs it through Solution.minValidStrings(),
 * and prints the result serialized to match this task's official evaluator_py.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        Solution sol = new Solution();
        String[] tok = line.split(" ", -1);
        var result = sol.minValidStrings(TestUtil.parseStringArray(tok[0]), tok[1]);
        System.out.println("###RESULT###");
        System.out.println(result);
    }
}
