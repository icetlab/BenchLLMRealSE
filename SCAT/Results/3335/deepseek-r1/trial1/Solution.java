class Solution {
    public int minimumOperationsToWriteY(int[][] grid) {
        int n = grid.length;
        int mid = n / 2;
        int[] allCnt = new int[3];
        int[] yCnt = new int[3];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                allCnt[grid[i][j]]++;
            }
        }

        for (int i = 0; i < mid; i++) {
            yCnt[grid[i][i]]++;
            yCnt[grid[i][n - 1 - i]]++;
        }
        for (int i = mid; i < n; i++) {
            yCnt[grid[i][mid]]++;
        }

        int[] otherCnt = new int[3];
        for (int v = 0; v < 3; v++) {
            otherCnt[v] = allCnt[v] - yCnt[v];
        }

        int totalY = yCnt[0] + yCnt[1] + yCnt[2];
        int totalOther = n * n - totalY;
        int minPrice = Integer.MAX_VALUE;

        for (int y = 0; y < 3; y++) {
            for (int other = 0; other < 3; other++) {
                if (y == other) {
                    continue;
                }
                int price = (totalY - yCnt[y]) + (totalOther - otherCnt[other]);
                if (price < minPrice) {
                    minPrice = price;
                }
            }
        }
        return minPrice;
    }
}