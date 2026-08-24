package effibenchjmh.task3313;

import java.util.*;
import java.util.stream.*;

class Solution {
    public long maximumStrength(int[] nums, int k) {
        long[][] dp = new long[k + 1][nums.length + 1];
        for (int i = 1; i <= k; i++) {
            long maxSum = Long.MIN_VALUE /2;
            long curr = Long.MIN_VALUE/2;
            long multiplier = (i % 2 == 1) ? (k + 1 - i) : (i - 1 - k);
            for (int j = i - 1; j < nums.length; j++) {
                curr = Math.max(curr + nums[j] * multiplier, dp[i - 1][j] + nums[j] * multiplier);
                maxSum = Math.max(maxSum, curr);
                dp[i][j + 1] = maxSum;
            }
        }
        return dp[k][nums.length];
    }
}
