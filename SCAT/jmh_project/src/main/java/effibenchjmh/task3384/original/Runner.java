package effibenchjmh.task3384.original;

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id=3384 | Minimum Number of Operations to Make Word K-Periodic
 * Reads ONE test case's raw input from stdin, runs it through Solution.minimumOperationsToMakeKPeriodic(),
 * and prints the result serialized to match this task's official evaluator_py.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        Solution sol = new Solution();
        String[] tok = line.split(" ", -1);
        var result = sol.minimumOperationsToMakeKPeriodic(tok[0], Integer.parseInt(tok[1]));
        System.out.println("###RESULT###");
        System.out.println(result);
    }
}
