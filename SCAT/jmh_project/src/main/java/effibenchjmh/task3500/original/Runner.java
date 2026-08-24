package effibenchjmh.task3500.original;

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id=3500 | Minimum Cost for Cutting Cake II
 * Reads ONE test case's raw input from stdin, runs it through Solution.minimumCost(),
 * and prints the result serialized to match this task's official evaluator_py.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        Solution sol = new Solution();
        String[] tok = line.split(" ", -1);
        String[] cuts = tok[2].split(";", -1);
        var result = sol.minimumCost(Integer.parseInt(tok[0]), Integer.parseInt(tok[1]), TestUtil.parseIntArray(cuts[0]), TestUtil.parseIntArray(cuts[1]));
        System.out.println("###RESULT###");
        System.out.println(result);
    }
}
