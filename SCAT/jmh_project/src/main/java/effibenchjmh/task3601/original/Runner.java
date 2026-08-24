package effibenchjmh.task3601.original;

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id=3601 | Find the K-th Character in String Game II
 * Reads ONE test case's raw input from stdin, runs it through Solution.kthCharacter(),
 * and prints the result serialized to match this task's official evaluator_py.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        Solution sol = new Solution();
        String[] tok = line.split(" ", -1);
        var result = sol.kthCharacter(Long.parseLong(tok[1]), TestUtil.parseIntArray(tok[0]));
        System.out.println("###RESULT###");
        System.out.println(result);
    }
}
