package effibenchjmh.task3335.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    Set<Integer> yCells = new HashSet<>();

    public int minimumOperationsToWriteY(int[][] grid) {
        int minPrice = Integer.MAX_VALUE;
        int currPrice = 0;
        int n = grid.length;
        markY(grid);
        //y = 0, other 1
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (yCells.contains(i * n + j)) {
                    if (grid[i][j] != 0) {
                        currPrice++;
                    }
                } else {
                    if (grid[i][j] != 1) {
                        currPrice++;
                    }
                }
            }
        }
        if (minPrice > currPrice) {
            minPrice = currPrice;
        }
        currPrice = 0;

        //y = 0, other 2
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (yCells.contains(i * n + j)) {
                    if (grid[i][j] != 0) {
                        currPrice++;
                    }
                } else {
                    if (grid[i][j] != 2) {
                        currPrice++;
                    }
                }
            }
        }
        if (minPrice > currPrice) {
            minPrice = currPrice;
        }
        currPrice = 0;

        //y = 1, other 0
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (yCells.contains(i * n + j)) {
                    if (grid[i][j] != 1) {
                        currPrice++;
                    }
                } else {
                    if (grid[i][j] != 0) {
                        currPrice++;
                    }
                }
            }
        }
        if (minPrice > currPrice) {
            minPrice = currPrice;
        }
        currPrice = 0;

        //y = 1, other 2
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (yCells.contains(i * n + j)) {
                    if (grid[i][j] != 1) {
                        currPrice++;
                    }
                } else {
                    if (grid[i][j] != 2) {
                        currPrice++;
                    }
                }
            }
        }
        if (minPrice > currPrice) {
            minPrice = currPrice;
        }
        currPrice = 0;

        //y = 2, other 0
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (yCells.contains(i * n + j)) {
                    if (grid[i][j] != 2) {
                        currPrice++;
                    }
                } else {
                    if (grid[i][j] != 0) {
                        currPrice++;
                    }
                }
            }
        }
        if (minPrice > currPrice) {
            minPrice = currPrice;
        }
        currPrice = 0;

        //y = 2, other 1
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (yCells.contains(i * n + j)) {
                    if (grid[i][j] != 2) {
                        currPrice++;
                    }
                } else {
                    if (grid[i][j] != 1) {
                        currPrice++;
                    }
                }
            }
        }
        if (minPrice > currPrice) {
            minPrice = currPrice;
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
