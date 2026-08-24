package effibenchjmh.task3516.original;

import java.util.*;
import java.util.stream.*;

class NeighborSum
{
    int [][] g;
    int [][] v;
    int n;
    HashMap<Integer,HashMap<Integer,int[]>> h;
    
    public NeighborSum(int[][] grid)
    {
        g = grid;
        n = g.length;
        v = new int[n*n][2];
        h = new HashMap<>();
        int [] a;

        for(int i=0; i<n; i++)
            for(int j=0; j<n; j++)
            {
                a = v[g[i][j]];
                a[0] = i;
                a[1] = j;
            }
    }
    
    public int adjacentSum(int value)
    {
        return get(v[value])[0];
    }
    
    public int diagonalSum(int value)
    {
        return get(v[value])[1];
    }

    private int [] get(int [] a)
    {
        return h.computeIfAbsent(a[0],k->new HashMap<>()).computeIfAbsent(a[1],j->make(a[0],a[1]));
    }

    private int [] make(int x, int y)
    {
        return new int[]
        {   (x>0 ? g[x-1][y] : 0)
            + (x<n-1 ? g[x+1][y] : 0)
            + (y>0 ? g[x][y-1] : 0)
            + (y<n-1 ? g[x][y+1] : 0),

            (x>0 && y>0 ? g[x-1][y-1] : 0)
            + (x<n-1 && y<n-1 ? g[x+1][y+1] : 0)
            + (x>0 && y<n-1 ? g[x-1][y+1] : 0)
            + (x<n-1 && y>0 ? g[x+1][y-1] : 0)
        };
    }
}
