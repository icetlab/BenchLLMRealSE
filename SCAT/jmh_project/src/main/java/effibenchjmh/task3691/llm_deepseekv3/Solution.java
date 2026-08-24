package effibenchjmh.task3691.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int minimumOperations(int[][] grid) {
        int count = 0;
        for (int j = 0; j < grid[0].length; j++) {
            int prev = grid[0][j];
            for (int i = 1; i < grid.length; i++) {
                if (grid[i][j] <= prev) {
                    int newVal = prev + 1;
                    count += newVal - grid[i][j];
                    grid[i][j] = newVal;
                }
                prev = grid[i][j];
            }
        }
        return count;
    }
}
