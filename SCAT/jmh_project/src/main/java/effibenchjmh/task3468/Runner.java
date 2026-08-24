package effibenchjmh.task3468;

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id=3468 | Find the Encrypted String
 * Reads ONE test case's raw input from stdin, runs it through Solution.getEncryptedString(),
 * and prints the result serialized to match this task's official evaluator_py.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        Solution sol = new Solution();
        String[] tok = line.split(" ", -1);
        var result = sol.getEncryptedString(tok[0], Integer.parseInt(tok[1]));
        System.out.println("###RESULT###");
        System.out.println(result);
    }
}
