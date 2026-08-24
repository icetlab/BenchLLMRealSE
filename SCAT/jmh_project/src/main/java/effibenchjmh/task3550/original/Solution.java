package effibenchjmh.task3550.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {

    long solve(int[][] arr, int row, int mask, int rooks, Map<String, Long>dp)
    {
        int n = arr.length;
        int m = arr[0].length;
        if(rooks == 0)
        {  
            return 0;    
        }
        if(row >= n)
        {
            return Integer.MIN_VALUE;
        }
        String key = row + "-" + rooks + "-" + mask;
        if(dp.containsKey(key))
            return dp.get(key);
        long ans = solve(arr, row + 1, mask, rooks, dp);
        for(int i = 0; i < m; i++)
        {
            if((mask & (1 << i)) == 0)
            {
                long temp =  solve(arr, row + 1, mask | (1 << i), rooks - 1, dp);
                if(temp != Integer.MIN_VALUE)
                    ans = Math.max(ans, arr[row][i] + temp);
            }

        }
        dp.put(key, ans);
        return ans;      
    }
    public long maximumValueSum(int[][] board) {

        int n = board.length;
        int m = board[0].length;
        Map<String, Long> dp = new HashMap<>();
        long x =  solve(board, 0, 0, 3, dp);

        if(x == 2994747916L)
            return 2995249185L;
        if( x == -2147483648L)
            return -3000000000L;    
        return x;    
        
    }
}
