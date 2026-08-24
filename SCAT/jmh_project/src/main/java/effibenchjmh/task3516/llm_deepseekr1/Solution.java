package effibenchjmh.task3516.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class NeighborSum
{
    private int[][] g;
    private int n;
    private int[] adjSum;
    private int[] diagSum;
    
    public NeighborSum(int[][] grid)
    {
        g = grid;
        n = g.length;
        int N = n * n;
        adjSum = new int[N];
        diagSum = new int[N];
        
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                int val = g[i][j];
                adjSum[val] = (i > 0 ? g[i-1][j] : 0)
                            + (i < n-1 ? g[i+1][j] : 0)
                            + (j > 0 ? g[i][j-1] : 0)
                            + (j < n-1 ? g[i][j+1] : 0);
                diagSum[val] = (i > 0 && j > 0 ? g[i-1][j-1] : 0)
                            + (i < n-1 && j < n-1 ? g[i+1][j+1] : 0)
                            + (i > 0 && j < n-1 ? g[i-1][j+1] : 0)
                            + (i < n-1 && j > 0 ? g[i+1][j-1] : 0);
            }
        }
    }
    
    public int adjacentSum(int value)
    {
        return adjSum[value];
    }
    
    public int diagonalSum(int value)
    {
        return diagSum[value];
    }
}
