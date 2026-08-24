package effibenchjmh.task3733;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int lenOfVDiagonal(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[][] dirs = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        int max = 0;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] != 1) continue;
                
                for (int[] dir : dirs) {
                    int dx = dir[0];
                    int dy = dir[1];
                    int steps = 0;
                    
                    while (true) {
                        int nextX = i + (steps + 1) * dx;
                        int nextY = j + (steps + 1) * dy;
                        if (nextX < 0 || nextX >= n || nextY < 0 || nextY >= m) break;
                        int step = steps + 1;
                        int expected = (step % 2 == 1) ? 2 : 0;
                        if (grid[nextX][nextY] == expected) {
                            steps++;
                        } else {
                            break;
                        }
                    }
                    
                    int currentMax = steps + 1;
                    
                    for (int t = 0; t <= steps; t++) {
                        int ndx = dy;
                        int ndy = -dx;
                        int x = i + t * dx + ndx;
                        int y = j + t * dy + ndy;
                        if (x < 0 || x >= n || y < 0 || y >= m) continue;
                        int stepInTurn = t + 1;
                        int expected = (stepInTurn % 2 == 1) ? 2 : 0;
                        if (grid[x][y] != expected) continue;
                        
                        int s = 0;
                        while (true) {
                            int nextX = x + (s + 1) * ndx;
                            int nextY = y + (s + 1) * ndy;
                            if (nextX < 0 || nextX >= n || nextY < 0 || nextY >= m) break;
                            int step = stepInTurn + (s + 1);
                            expected = (step % 2 == 1) ? 2 : 0;
                            if (grid[nextX][nextY] == expected) {
                                s++;
                            } else {
                                break;
                            }
                        }
                        
                        int total = (t + 1) + (s + 1);
                        if (total > currentMax) {
                            currentMax = total;
                        }
                    }
                    
                    if (currentMax > max) {
                        max = currentMax;
                    }
                }
            }
        }
        
        return max;
    }
}
