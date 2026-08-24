package effibenchjmh.task3492.llm_deepseekv3;

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
        int[][] xPrefix = new int[m][n];
        int[][] yPrefix = new int[m][n];
        int count = 0;

        // initializing our prefixSum arrays
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'X') {
                    xPrefix[i][j] = 1;
                } else if (grid[i][j] == 'Y') {
                    yPrefix[i][j] = 1;
                }
            }
        }

        // prefixSum of each row
        for (int i = 0; i < m; i++) {
            for (int j = 1; j < n; j++) {
                xPrefix[i][j] += xPrefix[i][j-1];
                yPrefix[i][j] += yPrefix[i][j-1];
            }
        }

        // prefixSum of each col
        for (int j = 0; j < n; j++) {
            for (int i = 1; i < m; i++) {
                xPrefix[i][j] += xPrefix[i-1][j];
                yPrefix[i][j] += yPrefix[i-1][j];
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (xPrefix[i][j] != 0 && xPrefix[i][j] == yPrefix[i][j]) {
                    count++;
                }
            }
        }

        return count;
    }
}
