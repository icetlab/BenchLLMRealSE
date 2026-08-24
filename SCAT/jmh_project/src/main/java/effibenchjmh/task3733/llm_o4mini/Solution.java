package effibenchjmh.task3733.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;


import java.util.Arrays;

class Solution {

    int[][] dirs = {
        {-1, 1}, // topRight
        {1, 1}, // bottomRight
        {1, -1}, // bottomLeft
        {-1, -1} // topLeft
    };
    int res = 0;
    int[][][][][] memo;
    public int lenOfVDiagonal(int[][] grid) {
        memo = new int[grid.length][grid[0].length][dirs.length][2][2];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                for (int d = 0; d < dirs.length; d++) {
                    for (int t = 0; t < 2; t++) {
                        Arrays.fill(memo[i][j][d][t], -1);
                    }
                }
            }
        }
 
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                
                    for (int k = 0; k < dirs.length; k++) {
                        int sub = dfs(grid,2, i, j, k, false);
                        res = Math.max(sub, res);
                    }
                    
                }
                
            }
        }
        return res;
    }

    private int dfs(int[][] grid, int target, int i, int j, int d, boolean turned) {
        int turnedIdx = turned ? 1 : 0;
        int tIdx = (target == 2) ? 0 : 1;
        if (memo[i][j][d][turnedIdx][tIdx] != -1) return memo[i][j][d][turnedIdx][tIdx];
        int[] dir = dirs[d];
        int newX = i + dir[0];
        int newY = j + dir[1];
        if (newX < 0 || newY < 0 || newX >= grid.length || newY >= grid[0].length || grid[newX][newY] != target) return 1;


        int turn = 0;
        int straight = dfs(grid, target == 2 ? 0 : 2, newX, newY, d, turned) + 1;
        if (!turned) turn = dfs(grid, target == 2 ? 0 : 2, newX, newY, (d + 1)%4, true) + 1;
        int ans = Math.max(turn, straight);
        memo[i][j][d][turnedIdx][tIdx] = ans;
        return ans;

    }
}
