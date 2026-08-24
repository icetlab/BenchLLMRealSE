package effibenchjmh.task3201;

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id=3201 | Distribute Candies Among Children II
 * Reads ONE test case's raw input from stdin, runs it through Solution.distributeCandies(),
 * and prints the result serialized to match this task's official evaluator_py.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        Solution sol = new Solution();
        String[] tok = line.split(",", -1);
        var result = sol.distributeCandies(Integer.parseInt(tok[0]), Integer.parseInt(tok[1]));
        System.out.println("###RESULT###");
        System.out.println(result);
    }
}
