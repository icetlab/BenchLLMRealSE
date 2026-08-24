package effibenchjmh.task3536.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    public int countOfPairs(int[] nums) {
        int n=nums.length;
        int mod=1000000007;
        long dp[][]=new long[n+1][1002];
        for(int j=0;j<=nums[n-1];j++) dp[n-1][j]=1;
        for(int j=1;j<=1000;j++) dp[n-1][j]+=dp[n-1][j-1];
        for(int i=n-2;i>=0;i--){
            for(int j=0;j<=nums[i];j++){
                int l=Math.max(j,nums[i+1]-nums[i]+j);
                int h=nums[i+1];
                dp[i][j]=(dp[i+1][h])%mod;
                if(l>0) dp[i][j]=(dp[i][j]-dp[i+1][l-1])%mod;
                dp[i][j]+=mod;
                dp[i][j]%=mod;
            }
            for(int j=1;j<=1000;j++){
                dp[i][j]+=dp[i][j-1];
                dp[i][j]%=mod;
            }
        }
        return (int)dp[0][1000];
    }
}
