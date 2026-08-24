package effibenchjmh.task3194;

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id=3194 | Find Words Containing Character
 * Reads ONE test case's raw input from stdin, runs it through Solution.findWordsContaining(),
 * and prints the result serialized to match this task's official evaluator_py.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        Solution sol = new Solution();
        String[] tok = line.split(" ", -1);
        var result = sol.findWordsContaining(TestUtil.parseStringArray(tok[0]), tok[1].trim().charAt(0));
        System.out.println("###RESULT###");
        System.out.println(result.stream().map(String::valueOf).collect(java.util.stream.Collectors.joining(",")));
    }
}
