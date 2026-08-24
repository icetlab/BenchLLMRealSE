package effibenchjmh.task3542.original;

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id=3542 | Maximum Value Sum by Placing Three Rooks II
 * Reads ONE test case's raw input from stdin, runs it through Solution.maximumValueSum(),
 * and prints the result serialized to match this task's official evaluator_py.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        Solution sol = new Solution();
        String[] tok = line.split(";", -1);
        int mrows = Integer.parseInt(tok[0]);
        int[][] board = new int[mrows][];
        for (int i = 0; i < mrows; i++) board[i] = TestUtil.parseIntArray(tok[2 + i]);
        var result = sol.maximumValueSum(board);
        System.out.println("###RESULT###");
        System.out.println(result);
    }
}
