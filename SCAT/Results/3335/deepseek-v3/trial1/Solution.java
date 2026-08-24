class Solution {
    Set<Integer> yCells = new HashSet<>();

    public int minimumOperationsToWriteY(int[][] grid) {
        int n = grid.length;
        markY(grid);
        
        // Count occurrences of 0,1,2 in Y cells and non-Y cells
        int[] yCount = new int[3];
        int[] otherCount = new int[3];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int val = grid[i][j];
                if (yCells.contains(i * n + j)) {
                    yCount[val]++;
                } else {
                    otherCount[val]++;
                }
            }
        }
        
        int yTotal = yCount[0] + yCount[1] + yCount[2];
        int otherTotal = otherCount[0] + otherCount[1] + otherCount[2];
        
        int minPrice = Integer.MAX_VALUE;
        // Try all 6 combinations: yValue in {0,1,2}, otherValue in {0,1,2} with yValue != otherValue
        for (int yVal = 0; yVal < 3; yVal++) {
            for (int otherVal = 0; otherVal < 3; otherVal++) {
                if (yVal == otherVal) continue;
                int price = (yTotal - yCount[yVal]) + (otherTotal - otherCount[otherVal]);
                minPrice = Math.min(minPrice, price);
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