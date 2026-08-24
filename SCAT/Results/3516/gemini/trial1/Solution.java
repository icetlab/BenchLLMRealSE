class NeighborSum
{
    int [][] v;
    int[][][] sumsCache;
    
    public NeighborSum(int[][] grid)
    {
        int n = grid.length;
        v = new int[n*n][2];
        
        // Populate the value-to-coordinate map
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                v[grid[i][j]][0] = i;
                v[grid[i][j]][1] = j;
            }
        }

        // Pre-compute all sums and store them in a cache
        sumsCache = new int[n][n][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Adjacent sum
                sumsCache[i][j][0] = (i > 0 ? grid[i - 1][j] : 0)
                                   + (i < n - 1 ? grid[i + 1][j] : 0)
                                   + (j > 0 ? grid[i][j - 1] : 0)
                                   + (j < n - 1 ? grid[i][j + 1] : 0);
                
                // Diagonal sum
                sumsCache[i][j][1] = (i > 0 && j > 0 ? grid[i - 1][j - 1] : 0)
                                   + (i < n - 1 && j < n - 1 ? grid[i + 1][j + 1] : 0)
                                   + (i > 0 && j < n - 1 ? grid[i - 1][j + 1] : 0)
                                   + (i < n - 1 && j > 0 ? grid[i + 1][j - 1] : 0);
            }
        }
    }
    
    public int adjacentSum(int value)
    {
        int[] coords = v[value];
        return sumsCache[coords[0]][coords[1]][0];
    }
    
    public int diagonalSum(int value)
    {
        int[] coords = v[value];
        return sumsCache[coords[0]][coords[1]][1];
    }
}