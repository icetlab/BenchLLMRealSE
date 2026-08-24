package effibenchjmh.task3335.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    Set<Integer> yCells = new HashSet<>();

    public int minimumOperationsToWriteY(int[][] grid) {
        yCells.clear();
        int n = grid.length;
        markY(grid);

        int[] yCounts = new int[3];
        int[] otherCounts = new int[3];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int val = grid[i][j];
                if (yCells.contains(i * n + j)) {
                    yCounts[val]++;
                } else {
                    otherCounts[val]++;
                }
            }
        }

        int ySize = yCells.size();
        int otherSize = n * n - ySize;
        int minPrice = Integer.MAX_VALUE;

        for (int yVal = 0; yVal <= 2; yVal++) {
            for (int otherVal = 0; otherVal <= 2; otherVal++) {
                if (yVal == otherVal) {
                    continue;
                }
                int currentPrice = (ySize - yCounts[yVal]) + (otherSize - otherCounts[otherVal]);
                minPrice = Math.min(minPrice, currentPrice);
            }
        }
        return minPrice;
    }

    public void markY(int[][] grid) {
        int mid = grid.length / 2;
        int n = grid.length;
        int i = 0;
        int j = 0;
        while (i != mid) {
            yCells.add(i * n + j);
            i++;
            j++;
        }
        i = 0;
        j = n - 1;
        while (i != j) {
            yCells.add(i * n + j);
            i++;
            j--;
        }
        i = mid;
        j = mid;
        while (i < n) {
            yCells.add(i * n + j);
            i++;
        }
    }
}
