class Solution {
    Set<Integer> yCells = new HashSet<>();

    public int minimumOperationsToWriteY(int[][] grid) {
        int n = grid.length;
        int mid = n / 2;
        int[] cntY = new int[3], cntO = new int[3];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int v = grid[i][j];
                boolean isY = (i < mid && i == j)
                        || (i < mid && i + j == n - 1)
                        || (i >= mid && j == mid);
                if (isY) cntY[v]++;
                else cntO[v]++;
            }
        }
        int totalY = cntY[0] + cntY[1] + cntY[2];
        int totalO = cntO[0] + cntO[1] + cntO[2];
        int minPrice = Integer.MAX_VALUE;
        for (int cY = 0; cY < 3; cY++) {
            for (int cO = 0; cO < 3; cO++) {
                if (cY == cO) continue;
                int cost = (totalY - cntY[cY]) + (totalO - cntO[cO]);
                minPrice = Math.min(minPrice, cost);
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