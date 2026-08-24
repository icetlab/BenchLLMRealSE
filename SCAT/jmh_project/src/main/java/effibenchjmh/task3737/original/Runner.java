package effibenchjmh.task3737.original;

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id=3737 | Paint House IV
 * Reads ONE test case's raw input from stdin, runs it through Solution.minCost(),
 * and prints the result serialized to match this task's official evaluator_py.
 * Used for correctness verification (not performance measurement -- see Bench.java
 * in this package for the JMH benchmark).
 *
 * NOTE: reads stdin via readAllBytes(), NOT line-by-line via BufferedReader.readLine().
 * readLine()-based loops silently drop a trailing empty line before EOF (there is no
 * way to distinguish "no more input" from "one final empty line" that way), which
 * breaks tasks whose format encodes an empty trailing segment (e.g. an empty array
 * parameter). readAllBytes() preserves the exact input bytes, matching how the JMH
 * benchmark reads its resource case files.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        Solution sol = new Solution();
        int[][] costMatrix = TestUtil.parseIntMatrixRowCountHeader(line);
        var result = sol.minCost(costMatrix.length, costMatrix);
        System.out.println("###RESULT###");
        System.out.println(result);
    }
}
