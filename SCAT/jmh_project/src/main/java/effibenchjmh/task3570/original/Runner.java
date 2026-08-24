package effibenchjmh.task3570.original;

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id=3570 | Count of Substrings Containing Every Vowel and K Consonants I
 * Reads ONE test case's raw input from stdin, runs it through Solution.countOfSubstrings(),
 * and prints the result serialized to match this task's official evaluator_py.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        Solution sol = new Solution();
        String[] tok = line.split(" ", -1);
        var result = sol.countOfSubstrings(tok[0], Integer.parseInt(tok[1]));
        System.out.println("###RESULT###");
        System.out.println(result);
    }
}
