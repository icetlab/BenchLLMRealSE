package effibenchjmh.task3492;

import effibenchjmh.common.TestUtil;
import java.nio.charset.StandardCharsets;

/**
 * id=3492 | Count Submatrices With Equal Frequency of X and Y
 * Reads ONE test case's raw input from stdin, runs it through Solution.numberOfSubmatrices(),
 * and prints the result serialized to match this task's official evaluator_py.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        String line = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);

        Solution sol = new Solution();
        String[] tok = line.split(";", -1);
        char[][] grid = new char[tok.length - 1][];
        for (int i = 1; i < tok.length; i++) {
            String[] cells = tok[i].split(",", -1);
            grid[i - 1] = new char[cells.length];
            for (int j = 0; j < cells.length; j++) grid[i - 1][j] = cells[j].trim().charAt(0);
        }
        var result = sol.numberOfSubmatrices(grid);
        System.out.println("###RESULT###");
        System.out.println(result);
    }
}
