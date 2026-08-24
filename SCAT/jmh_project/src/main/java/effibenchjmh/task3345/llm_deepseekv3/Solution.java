package effibenchjmh.task3345.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int mod = (int) 1e9+7;
    public int sumOfPower(int[] nums, int k) {
        int n = nums.length;
        int[] cnt = new int[n + 1];
        cnt[0] = 1;
        for (int i = 1; i <= n; i++) {
            cnt[i] = (2 * cnt[i-1]) % mod;
        }
        int[][] dp = new int[k + 1][n + 1];
        dp[0][0] = 1;
        for (int num : nums) {
            for (int s = k; s >= num; s--) {
                for (int c = n; c >= 1; c--) {
                    dp[s][c] = (dp[s][c] + dp[s - num][c - 1]) % mod;
                }
            }
        }
        int ans = 0;
        for (int c = 1; c <= n; c++) {
            ans = (ans + (int)((long)dp[k][c] * cnt[n - c] % mod)) % mod;
        }
        return ans;
    }
}
