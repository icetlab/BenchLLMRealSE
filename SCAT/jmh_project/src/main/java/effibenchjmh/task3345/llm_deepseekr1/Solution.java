package effibenchjmh.task3345.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int mod = (int) 1e9+7;
    public int f(int i,int[] nums,int k,int ele,int[] cnt,int[][][] dp) {
        if(i == nums.length) {
            if(k == 0) {
                return cnt[nums.length - ele]; 
            }
            return 0; 
        }
        if(k < 0) return 0;
        if (dp[i][k][ele] != -1) return dp[i][k][ele];
        int pick = f(i+1,nums,k-nums[i],ele+1,cnt,dp);
        int not = f(i+1,nums,k,ele,cnt,dp);
        return dp[i][k][ele] = (pick + not) % mod;
    }
    public int sumOfPower(int[] nums, int k) {
        if (k < 0) return 0;
        final long INV2 = (mod + 1L) / 2;
        int n = nums.length;
        long[] dp = new long[k + 1];
        dp[0] = 1;

        for (int x : nums) {
            for (int s = k; s >= x; s--) {
                dp[s] = (dp[s] + dp[s - x] * INV2) % mod;
            }
        }

        long pow2 = 1;
        for (int i = 0; i < n; i++) {
            pow2 = (pow2 * 2) % mod;
        }
        return (int) (dp[k] * pow2 % mod);
    }
}
