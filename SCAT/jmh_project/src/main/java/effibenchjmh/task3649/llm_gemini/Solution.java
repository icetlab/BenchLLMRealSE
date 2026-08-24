package effibenchjmh.task3649.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int findMinimumTime(List<Integer> strength, int k) {
        int n = strength.size();
        if (n == 0) {
            return 0;
        }

        // dp[mask] stores the minimum time for the subset of locks in the bitmask.
        long[] dp = new long[1 << n];
        java.util.Arrays.fill(dp, Long.MAX_VALUE);
        dp[0] = 0;

        for (int mask = 1; mask < (1 << n); mask++) {
            int assignedCount = Integer.bitCount(mask);
            long divisor = 1L + (long) (assignedCount - 1) * k;

            for (int j = 0; j < n; j++) {
                // If the j-th lock is in the current subset (mask).
                if ((mask & (1 << j)) != 0) {
                    int prevMask = mask ^ (1 << j);
                    if (dp[prevMask] != Long.MAX_VALUE) {
                        long currentStrength = strength.get(j);
                        // Calculate time using integer arithmetic for ceiling division.
                        long time = (currentStrength + divisor - 1) / divisor;
                        dp[mask] = Math.min(dp[mask], dp[prevMask] + time);
                    }
                }
            }
        }

        return (int) dp[(1 << n) - 1];
    }
}
