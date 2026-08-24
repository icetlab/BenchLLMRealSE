package effibenchjmh.task3654.llm_deepseekv3;

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
        int val=nums[i];
        if(op1>0){
            int newval=(val+1)/2;
            int ap_op1=newval+solve(nums,k,op1-1,op2,i+1,dp);
            res=Math.min(res,ap_op1);
        }
        if(op2>0 && val>=k){
            int newval=val-k;
            int ap_op2=newval+solve(nums,k,op1,op2-1,i+1,dp);
            res=Math.min(res,ap_op2);
        }
        if(op1>0 && op2>0 && val>=k){
            int half=(val+1)/2;
            int newval=half>=k ? half-k : half;
            int ap_op1_op2=newval+solve(nums,k,op1-1,op2-1,i+1,dp);
            res=Math.min(res,ap_op1_op2);
        }
        if(op1>0 && op2>0 && val>=k){
            int newval=val-k;
            newval=(newval+1)/2;
            int ap_op2_op1=newval+solve(nums,k,op1-1,op2-1,i+1,dp);
            res=Math.min(res,ap_op2_op1);
        }
        res=Math.min(res,val+solve(nums,k,op1,op2,i+1,dp));
        return dp[i][op1][op2]=res;

    }
    public int minArraySum(int[] nums, int k, int op1, int op2) {
        int n=nums.length;
        int dp[][][]=new int[n+1][op1+1][op2+1];
        for(int i=0;i<=n;i++){
            for(int j=0;j<=op1;j++){
                for(int k1=0;k1<=op2;k1++){
                    dp[i][j][k1]=-1;
                }
            }
        }
        int res=solve(nums,k,op1,op2,0,dp);
        return res;
    }       
}
