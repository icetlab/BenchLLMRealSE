package effibenchjmh.task3691;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int minimumOperations(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int operations = 0;
        for (int col = 0; col < n; col++) {
            int current = grid[0][col];
            for (int row = 1; row < m; row++) {
                int needed = current + 1;
                if (grid[row][col] < needed) {
                    operations += (needed - grid[row][col]);
                    current = needed;
                } else {
                    current = grid[row][col];
                }
            }
        }
        return operations;
    }
}
