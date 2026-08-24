package effibenchjmh.task3536.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int countOfPairs(int[] nums) {
        int n=nums.length;
        int mod=1000000007;
        long dp[][]=new long[n+1][1002];
        for(int j=0;j<=nums[n-1];j++) dp[n-1][j]=1;
        for(int j=1;j<=1000;j++) dp[n-1][j]+=dp[n-1][j-1];
        long[] dppp = new long[1002];
        for(int i=n-2;i>=0;i--){
            dppp[0] = dp[i+1][0];
            for (int k = 1; k < 1002; k++) {
                dppp[k] = (dppp[k - 1] + dp[i+1][k]) % mod;
            }

            int h = nums[i+1];
            long C = dp[i+1][h];
            int d = nums[i+1] - nums[i];

            if (d <= 0) {
                for (int k = 0; k <= nums[i]; k++) {
                    long sum_g = (k > 0) ? dppp[k - 1] : 0;
                    long term1 = (1L * (k + 1) * C);
                    dp[i][k] = (term1 % mod - sum_g + mod) % mod;
                }
            } else { // d > 0
                long dppp_d_minus_2 = (d > 1) ? dppp[d - 2] : 0;
                for (int k = 0; k <= nums[i]; k++) {
                    long sum_g = (dppp[d + k - 1] - dppp_d_minus_2 + mod) % mod;
                    long term1 = (1L * (k + 1) * C);
                    dp[i][k] = (term1 % mod - sum_g + mod) % mod;
                }
            }

            if (nums[i] <= 1000) {
                long last_val = dp[i][nums[i]];
                for (int k = nums[i] + 1; k <= 1000; k++) {
                    dp[i][k] = last_val;
                }
            }
        }
        return (int)dp[0][1000];
    }
}
