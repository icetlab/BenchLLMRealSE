package effibenchjmh.task3576.original;

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id=3576 | Find Subtree Sizes After Changes
 * Reads ONE test case's raw input from stdin, runs it through Solution.findSubtreeSizes(),
 * and prints the result serialized to match this task's official evaluator_py.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        Solution sol = new Solution();
        String[] tok = line.split(" ", -1);
        var result = sol.findSubtreeSizes(TestUtil.parseIntArray(tok[0]), tok[1]);
        System.out.println("###RESULT###");
        System.out.println(java.util.stream.IntStream.of(result).mapToObj(String::valueOf).collect(java.util.stream.Collectors.joining(",")));
    }
}
