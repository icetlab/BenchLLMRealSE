package effibenchjmh.task3696.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public long countSubstrings(String s) {
        int n = s.length();
        long total = 0;

        for (int last = 1; last <= 9; last++) {
            long[] dp = new long[last]; // dp[rem] = count of substrings ending at i-1 with remainder rem
            for (int i = 0; i < n; i++) {
                int d = s.charAt(i) - '0';
                long[] new_dp = new long[last];

                // New substring of length 1, s[i]
                new_dp[d % last]++;

                // Extend all substrings ending at i-1
                for (int rem = 0; rem < last; rem++) {
                    int new_rem = (rem * 10 + d) % last;
                    new_dp[new_rem] += dp[rem];
                }

                dp = new_dp;

                if (d == last) {
                    total += dp[0];
                }
            }
        }
        return total;
    }
}

