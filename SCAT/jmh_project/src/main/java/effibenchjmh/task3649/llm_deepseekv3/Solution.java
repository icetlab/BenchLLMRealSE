package effibenchjmh.task3649.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int findMinimumTime(List<Integer> strength, int k) {
        int n = strength.size();
        int[] dp = new int[1 << n];
        for (int i = 0; i < (1 << n); i++) dp[i] = Integer.MAX_VALUE;
        dp[0] = 0;
        for (int mask = 0; mask < (1 << n); mask++) {
            if (dp[mask] == Integer.MAX_VALUE) continue;
            int processed = Integer.bitCount(mask);
            int x = 1 + processed * k;
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) == 0) {
                    int newMask = mask | (1 << i);
                    int time = dp[mask] + (int)Math.ceil((double)strength.get(i) / x);
                    if (time < dp[newMask]) {
                        dp[newMask] = time;
                    }
                }
            }
        }
        return dp[(1 << n) - 1];
    }
}
