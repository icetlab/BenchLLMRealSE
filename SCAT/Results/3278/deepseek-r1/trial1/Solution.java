import java.util.*;

class Solution {
    public int numberOfPairs(int[][] points) {
        int n = points.length;
        if (n < 2) return 0;
        
        // Coordinate compression
        int[] xs = new int[n];
        int[] ys = new int[n];
        for (int i = 0; i < n; i++) {
            xs[i] = points[i][0];
            ys[i] = points[i][1];
        }
        int[] sortedX = xs.clone();
        Arrays.sort(sortedX);
        int[] sortedY = ys.clone();
        Arrays.sort(sortedY);
        
        int[][] grid = new int[n][n];
        for (int i = 0; i < n; i++) {
            int xRank = Arrays.binarySearch(sortedX, points[i][0]);
            int yRank = Arrays.binarySearch(sortedY, points[i][1]);
            grid[xRank][yRank] = 1;
        }
        
        // Build 2D prefix sum
        int[][] P = new int[n + 1][n + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                P[i + 1][j + 1] = P[i][j + 1] + P[i + 1][j] - P[i][j] + grid[i][j];
            }
        }
        
        int count = 0;
        for (int i = 0; i < n; i++) {
            int x1 = xs[i];
            int y1 = ys[i];
            int xRank1 = Arrays.binarySearch(sortedX, x1);
            int yRank1 = Arrays.binarySearch(sortedY, y1);
            for (int j = i + 1; j < n; j++) {
                int x2 = xs[j];
                int y2 = ys[j];
                int xRank2 = Arrays.binarySearch(sortedX, x2);
                int yRank2 = Arrays.binarySearch(sortedY, y2);
                // check if i can be upper-left and j lower-right
                if (x1 <= x2 && y1 >= y2) {
                    int cnt = rectangleCount(P, xRank1, xRank2, yRank2, yRank1, n);
                    if (cnt == 2) count++;
                }
                // check reverse
                if (x2 <= x1 && y2 >= y1) {
                    int cnt = rectangleCount(P, xRank2, xRank1, yRank1, yRank2, n);
                    if (cnt == 2) count++;
                }
            }
        }
        return count;
    }
    
    private int rectangleCount(int[][] P, int x1, int x2, int y1, int y2, int n) {
        // inclusive rectangle from x1 to x2, y1 to y2, with x1 <= x2, y1 <= y2
        return P[x2 + 1][y2 + 1] - P[x1][y2 + 1] - P[x2 + 1][y1] + P[x1][y1];
    }
}