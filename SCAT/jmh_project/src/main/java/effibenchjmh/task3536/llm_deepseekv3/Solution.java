package effibenchjmh.task3536.llm_deepseekv3;

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
        for(int j=1;j<=1000;j++) dp[n-1][j]=(dp[n-1][j]+dp[n-1][j-1])%mod;
        for(int i=n-2;i>=0;i--){
            for(int j=0;j<=nums[i];j++){
                int l=Math.max(j,nums[i+1]-nums[i]+j);
                int h=nums[i+1];
                long val=dp[i+1][h];
                if(l>0) val=(val-dp[i+1][l-1]+mod)%mod;
                dp[i][j]=val;
            }
            for(int j=1;j<=1000;j++){
                dp[i][j]=(dp[i][j]+dp[i][j-1])%mod;
            }
        }
        return (int)dp[0][1000];
    }
}
