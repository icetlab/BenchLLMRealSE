package effibenchjmh.task3588;

import java.util.*;
import java.util.stream.*;

class Solution 
{
    static final int mod = 1000000007;
    public int countWinningSequences(String s) 
    {
        int n = s.length();
        Integer[][][] memo = new Integer[n][3][2005];

        return helper(s.toCharArray(), 0, 0, 1001, memo);
    }

    private int helper(char[] s, int start, int prev, int bob, Integer[][][] memo)
    {
        if(start == s.length) return bob > 1001 ? 1 : 0;
        if(memo[start][prev][bob] != null) return memo[start][prev][bob];

        long f = 0, w = 0, e = 0;
        char curr = s[start];

        if (curr == 'F') 
        {
            if (prev != 0 || start == 0) f = helper(s, start + 1, 0, bob, memo);
            if (prev != 1 || start == 0) w = helper(s, start + 1, 1, bob + 1, memo);
            if (prev != 2 || start == 0) e = helper(s, start + 1, 2, bob - 1, memo);
        } 
        else if (curr == 'W') 
        {
            if (prev != 0 || start == 0) f = helper(s, start + 1, 0, bob - 1, memo);
            if (prev != 1 || start == 0) w = helper(s, start + 1, 1, bob, memo);
            if (prev != 2 || start == 0) e = helper(s, start + 1, 2, bob + 1, memo);
        } 
        else if (curr == 'E') 
        {
            if (prev != 0 || start == 0) f = helper(s, start + 1, 0, bob + 1, memo);
            if (prev != 1 || start == 0) w = helper(s, start + 1, 1, bob - 1, memo);
            if (prev != 2 || start == 0) e = helper(s, start + 1, 2, bob, memo);
        }
        
        memo[start][prev][bob] = (int) ((f + w + e) % mod);
        return memo[start][prev][bob];
    }
}
