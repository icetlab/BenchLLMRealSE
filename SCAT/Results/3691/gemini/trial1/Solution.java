class Solution {
    public int minimumOperations(int[][] grid) {
        int m = grid.length;
        if (m == 0) {
            return 0;
        }
        int n = grid[0].length;
        long count = 0;

        for (int j = 0; j < n; j++) {
            int prev = -1; // Stores the required value for the previous row in the modified column.
            for (int i = 0; i < m; i++) {
                int required = prev + 1;
                if (grid[i][j] < required) {
                    count += required - grid[i][j];
                    prev = required;
                } else {
                    prev = grid[i][j];
                }
            }
        }
        return (int) count;
    }
}