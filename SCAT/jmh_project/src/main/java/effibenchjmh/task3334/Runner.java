package effibenchjmh.task3334;

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id=3334 | Apple Redistribution into Boxes
 * Reads ONE test case's raw input from stdin, runs it through Solution.minimumBoxes(),
 * and prints the result serialized to match this task's official evaluator_py.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        Solution sol = new Solution();
        String[] tok = line.split(" ", -1);
        var result = sol.minimumBoxes(TestUtil.parseIntArray(tok[0]), TestUtil.parseIntArray(tok[1]));
        System.out.println("###RESULT###");
        System.out.println(result);
    }
}
