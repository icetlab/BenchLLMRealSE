package effibenchjmh.task3313.original;

import java.util.*;
import java.util.stream.*;

import java.math.BigInteger;

class Solution {
    public long maximumStrength(int[] nums, int k) {
        BigInteger[][] dp = new BigInteger[k + 1][nums.length + 1];
        for (int i = 0; i <= k; i++) {
            for (int j = 0; j <= nums.length; j++) {
                dp[i][j] = BigInteger.ZERO;
            }
        }

        for (int i = 1; i <= k; i++) {
            BigInteger maxsum = BigInteger.valueOf(Long.MIN_VALUE);
            BigInteger curr = BigInteger.valueOf(Long.MIN_VALUE);
            int multiplier = (i % 2 == 1) ? k + 1 - i : i - 1 - k;
            for (int j = i - 1; j < nums.length; j++) {
                curr = curr.add(BigInteger.valueOf(nums[j]).multiply(BigInteger.valueOf(multiplier)));
                curr = curr.max(dp[i - 1][j].add(BigInteger.valueOf(nums[j]).multiply(BigInteger.valueOf(multiplier))));
                maxsum = maxsum.max(curr);
                dp[i][j + 1] = maxsum;
            }
        }

        return dp[k][nums.length].longValue();
    }
}
