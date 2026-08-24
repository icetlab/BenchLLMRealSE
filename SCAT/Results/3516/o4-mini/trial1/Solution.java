class NeighborSum
{
    int [][] g;
    int [][] v;
    int n;
    int[] adj;
    int[] diag;
    
    public NeighborSum(int[][] grid)
    {
        g = grid;
        n = g.length;
        v = new int[n*n][2];
        adj = new int[n*n];
        diag = new int[n*n];
        int [] a;

        for(int i=0; i<n; i++)
            for(int j=0; j<n; j++)
            {
                a = v[g[i][j]];
                a[0] = i;
                a[1] = j;
                int sAdj = (i>0 ? g[i-1][j] : 0)
                         + (i<n-1 ? g[i+1][j] : 0)
                         + (j>0 ? g[i][j-1] : 0)
                         + (j<n-1 ? g[i][j+1] : 0);
                int sDiag = (i>0 && j>0 ? g[i-1][j-1] : 0)
                          + (i<n-1 && j<n-1 ? g[i+1][j+1] : 0)
                          + (i>0 && j<n-1 ? g[i-1][j+1] : 0)
                          + (i<n-1 && j>0 ? g[i+1][j-1] : 0);
                adj[g[i][j]] = sAdj;
                diag[g[i][j]] = sDiag;
            }
    }
    
    public int adjacentSum(int value)
    {
        return adj[value];
    }
    
    public int diagonalSum(int value)
    {
        return diag[value];
    }


}