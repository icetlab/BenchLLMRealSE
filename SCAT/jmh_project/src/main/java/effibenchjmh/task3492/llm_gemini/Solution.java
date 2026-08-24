package effibenchjmh.task3492.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int numberOfSubmatrices(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] prefixX = new int[m + 1][n + 1];
        int[][] prefixY = new int[m + 1][n + 1];
        int count = 0;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int x = (grid[i - 1][j - 1] == 'X') ? 1 : 0;
                int y = (grid[i - 1][j - 1] == 'Y') ? 1 : 0;

                prefixX[i][j] = prefixX[i - 1][j] + prefixX[i][j - 1] - prefixX[i - 1][j - 1] + x;
                prefixY[i][j] = prefixY[i - 1][j] + prefixY[i][j - 1] - prefixY[i - 1][j - 1] + y;

                if (prefixX[i][j] > 0 && prefixX[i][j] == prefixY[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }
}
