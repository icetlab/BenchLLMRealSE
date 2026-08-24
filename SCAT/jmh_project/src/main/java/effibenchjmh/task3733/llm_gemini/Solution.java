package effibenchjmh.task3733.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;


class Solution {

    int[][] dirs = {
        {-1, 1}, // topRight
        {1, 1}, // bottomRight
        {1, -1}, // bottomLeft
        {-1, -1} // topLeft
    };
    Integer[][][][][] memo;

    public int lenOfVDiagonal(int[][] grid) {
        int rows = grid.length;
        if (rows == 0) return 0;
        int cols = grid[0].length;
        memo = new Integer[rows][cols][4][2][3]; // i, j, d, turned, target

        int res = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    for (int k = 0; k < dirs.length; k++) {
                        int sub = dfs(grid, 2, i, j, k, false);
                        res = Math.max(sub, res);
                    }
                }
            }
        }
        return res;
    }

    private int dfs(int[][] grid, int target, int i, int j, int d, boolean turned) {
        int turnedIdx = turned ? 1 : 0;
        if (memo[i][j][d][turnedIdx][target] != null) {
            return memo[i][j][d][turnedIdx][target];
        }

        int[] dir = dirs[d];
        int newX = i + dir[0];
        int newY = j + dir[1];
        if (newX < 0 || newY < 0 || newX >= grid.length || newY >= grid[0].length || grid[newX][newY] != target) return 1;

        int turn = 0;
        int straight = dfs(grid, target == 2 ? 0 : 2, newX, newY, d, turned) + 1;
        if (!turned) turn = dfs(grid, target == 2 ? 0 : 2, newX, newY, (d + 1) % 4, true) + 1;
        
        int result = Math.max(turn, straight);
        memo[i][j][d][turnedIdx][target] = result;
        return result;
    }
}
