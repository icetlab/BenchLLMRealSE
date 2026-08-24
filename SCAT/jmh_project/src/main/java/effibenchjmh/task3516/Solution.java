package effibenchjmh.task3516;

import java.util.*;
import java.util.stream.*;

class NeighborSum {
    private int[] suma = new int[101];  // Array to store sums of adjacent neighbors
    private int[] sumd = new int[101];  // Array to store sums of diagonal neighbors
    private final int[] adj = {0, 1, 0, -1, 0};  // Direction vectors for adjacent neighbors
    private final int[] dia = {1, 1, -1, -1, 1}; // Direction vectors for diagonal neighbors

    public NeighborSum(int[][] grid) {
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[i].length; ++j) {
                for (int d = 0; d < 4; ++d) {
                    add(i + adj[d], j + adj[d + 1], grid[i][j], suma, grid);
                    add(i + dia[d], j + dia[d + 1], grid[i][j], sumd, grid);
                }
            }
        }
    }

    private void add(int i, int j, int val, int[] arr, int[][] grid) {
        if (i >= 0 && j >= 0 && i < grid.length && j < grid[0].length) {
            arr[grid[i][j]] += val;
        }
    }

    public int adjacentSum(int value) {
        return suma[value];
    }

    public int diagonalSum(int value) {
        return sumd[value];
    }
}
