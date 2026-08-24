package effibenchjmh.task3550.original;

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id=3550 | Maximum Value Sum by Placing Three Rooks I
 * Reads ONE test case's raw input from stdin, runs it through Solution.maximumValueSum(),
 * and prints the result serialized to match this task's official evaluator_py.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        Solution sol = new Solution();
        String[] rows = line.split("\\|", -1);
        int[][] board = new int[rows.length][];
        for (int i = 0; i < rows.length; i++) board[i] = TestUtil.parseIntArray(rows[i]);
        var result = sol.maximumValueSum(board);
        System.out.println("###RESULT###");
        System.out.println(result);
    }
}
