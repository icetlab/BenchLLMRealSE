package effibenchjmh.task3654.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int solve(int nums[],int k,int op1,int op2,int i,int dp[][][]){
        int res=Integer.MAX_VALUE;
        if(dp[i][op1][op2]!=-1){
            return dp[i][op1][op2];
        }
        if(i>=nums.length){
            return 0;
        }
        if(op1>0){
            int newval = (nums[i] + 1) / 2;
            int ap_op1=newval+solve(nums,k,op1-1,op2,i+1,dp);
            res=Math.min(res,ap_op1);
        }
        if(op2>0 && nums[i]>=k){
            int newval=nums[i]-k;
            int ap_op2=newval+solve(nums,k,op1,op2-1,i+1,dp);
            res=Math.min(res,ap_op2);
        }
        if(op1>0 && op2>0 && nums[i]>=k){
            int newval = (nums[i] + 1) / 2;
            if(newval>=k){
                newval-=k;
            }
            int ap_op1_op2=newval+solve(nums,k,op1-1,op2-1,i+1,dp);
            res=Math.min(res,ap_op1_op2);
        }
        if(op1>0 && op2>0 && nums[i]>=k){
            int newval=nums[i]-k;
            newval = (newval + 1) / 2;
            int ap_op2_op1=newval+solve(nums,k,op1-1,op2-1,i+1,dp);
            res=Math.min(res,ap_op2_op1);
        }
        res=Math.min(res,nums[i]+solve(nums,k,op1,op2,i+1,dp));
        return dp[i][op1][op2]=res;

    }
    public int minArraySum(int[] nums, int k, int op1, int op2) {
        int[][][] dp = new int[nums.length + 1][op1 + 1][op2 + 1];
        for (int i = 0; i <= nums.length; i++) {
            for (int j = 0; j <= op1; j++) {
                java.util.Arrays.fill(dp[i][j], -1);
            }
        }
        return solve(nums, k, op1, op2, 0, dp);
    }       
}
