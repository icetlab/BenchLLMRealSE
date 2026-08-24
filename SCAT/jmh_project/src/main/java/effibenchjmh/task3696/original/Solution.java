package effibenchjmh.task3696.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    String s;
    int n;
    int[] digit;             
    long[][][] dp;           

    public long countSubstrings(String s) {
        this.s = s;
        this.n = s.length();
        digit = new int[n];
        for (int i = 0; i < n; i++) digit[i] = s.charAt(i) - '0';
        dp = new long[n + 1][10][11];
        for (long[][] layer : dp)
            for (long[] row : layer)
                Arrays.fill(row, -1);
        long total = 0;
        for (int last = 1; last <= 9; last++) {
            for (int start = 0; start < n; start++) {
                total += solve(start, last, 10);
            }
        }
        return total;
    }

    private long solve(int idx, int last, int prod) {
        if (idx == n) return 0;
        long memo = dp[idx][last][prod];
        if (memo != -1) return memo;
        int d = digit[idx];
        int newProd = (prod == 10)
                      ? d % last
                      : (int) ((prod * 10 + d) % last);

        long cnt = (d == last && newProd == 0) ? 1 : 0;
        cnt += solve(idx + 1, last, newProd);
        dp[idx][last][prod] = cnt;
        return cnt;
    }
}

