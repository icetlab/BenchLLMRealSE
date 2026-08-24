package effibenchjmh.task3516.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class NeighborSum
{
    int [][] g;
    int [][] v;
    int n;
    
    public NeighborSum(int[][] grid)
    {
        g = grid;
        n = g.length;
        v = new int[n*n][2];

        for(int i=0; i<n; i++)
            for(int j=0; j<n; j++)
            {
                v[g[i][j]][0] = i;
                v[g[i][j]][1] = j;
            }
    }
    
    public int adjacentSum(int value)
    {
        int x = v[value][0];
        int y = v[value][1];
        int sum = 0;
        if (x > 0) sum += g[x-1][y];
        if (x < n-1) sum += g[x+1][y];
        if (y > 0) sum += g[x][y-1];
        if (y < n-1) sum += g[x][y+1];
        return sum;
    }
    
    public int diagonalSum(int value)
    {
        int x = v[value][0];
        int y = v[value][1];
        int sum = 0;
        if (x > 0 && y > 0) sum += g[x-1][y-1];
        if (x < n-1 && y < n-1) sum += g[x+1][y+1];
        if (x > 0 && y < n-1) sum += g[x-1][y+1];
        if (x < n-1 && y > 0) sum += g[x+1][y-1];
        return sum;
    }
}
