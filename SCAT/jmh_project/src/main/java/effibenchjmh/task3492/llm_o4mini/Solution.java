package effibenchjmh.task3492.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;

class Solution {
    class Pair {
        int xCount;
        int yCount;

        public Pair(int xCount, int yCount) {
            this.xCount = xCount;
            this.yCount = yCount;
        }
    }

    public int numberOfSubmatrices(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] xPS = new int[m][n];
        int[][] yPS = new int[m][n];
        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int x = (grid[i][j] == 'X') ? 1 : 0;
                int y = (grid[i][j] == 'Y') ? 1 : 0;
                if (i > 0) {
                    x += xPS[i - 1][j];
                    y += yPS[i - 1][j];
                }
                if (j > 0) {
                    x += xPS[i][j - 1];
                    y += yPS[i][j - 1];
                }
                if (i > 0 && j > 0) {
                    x -= xPS[i - 1][j - 1];
                    y -= yPS[i - 1][j - 1];
                }
                xPS[i][j] = x;
                yPS[i][j] = y;
                if (x != 0 && x == y) {
                    count++;
                }
            }
        }
        return count;

    }
}
