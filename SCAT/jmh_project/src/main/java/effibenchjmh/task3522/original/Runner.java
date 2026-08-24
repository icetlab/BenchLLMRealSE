package effibenchjmh.task3522.original;

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id=3522 | Find the Power of K-Size Subarrays I
 * Reads ONE test case's raw input from stdin, runs it through Solution.resultsArray(),
 * and prints the result serialized to match this task's official evaluator_py.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        Solution sol = new Solution();
        String[] tok = line.split(" ", -1);
        var result = sol.resultsArray(TestUtil.parseIntArray(tok[0]), Integer.parseInt(tok[1]));
        System.out.println("###RESULT###");
        System.out.println(java.util.stream.IntStream.of(result).mapToObj(String::valueOf).collect(java.util.stream.Collectors.joining(",")));
    }
}
