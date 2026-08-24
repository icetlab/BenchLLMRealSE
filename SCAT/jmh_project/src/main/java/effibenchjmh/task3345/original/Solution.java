package effibenchjmh.task3345.original;

import java.util.*;
import java.util.stream.*;

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
        int[] cnt = new int[102];
        cnt[0] = 1;
        for (int i = 1; i <= 100; i++) {
            cnt[i] = (2 * cnt[i-1]) % mod;
        }
        int[][][] dp = new int[101][101][101];
        for(int i=0;i<dp.length;i++) {
            for(int j=0;j<dp[0].length;j++) {
                Arrays.fill(dp[i][j],-1);
            }
        }
        return f(0,nums,k,0,cnt,dp);
    }
}
