package effibenchjmh.task3696;

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id=3696 | Count Substrings Divisible By Last Digit
 * Reads ONE test case's raw input from stdin, runs it through Solution.countSubstrings(),
 * and prints the result serialized to match this task's official evaluator_py.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        Solution sol = new Solution();

        var result = sol.countSubstrings(line);
        System.out.println("###RESULT###");
        System.out.println(result);
    }
}
