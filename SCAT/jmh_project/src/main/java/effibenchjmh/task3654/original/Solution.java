package effibenchjmh.task3654.original;

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
            int newval=(int)Math.ceil(nums[i]/2.0);
            int ap_op1=newval+solve(nums,k,op1-1,op2,i+1,dp);
            res=Math.min(res,ap_op1);
        }
        if(op2>0 && nums[i]>=k){
            int newval=nums[i]-k;
            int ap_op2=newval+solve(nums,k,op1,op2-1,i+1,dp);
            res=Math.min(res,ap_op2);
        }
        if(op1>0 && op2>0 && nums[i]>=k){
            int newval=(int)Math.ceil(nums[i]/2.0);
            if(newval>=k){
                newval-=k;
            }
            int ap_op1_op2=newval+solve(nums,k,op1-1,op2-1,i+1,dp);
            res=Math.min(res,ap_op1_op2);
        }
        if(op1>0 && op2>0 && nums[i]>=k){
            int newval=nums[i]-k;;
            newval=(int)Math.ceil(newval/2.0);
            int ap_op2_op1=newval+solve(nums,k,op1-1,op2-1,i+1,dp);
            res=Math.min(res,ap_op2_op1);
        }
        res=Math.min(res,nums[i]+solve(nums,k,op1,op2,i+1,dp));
        return dp[i][op1][op2]=res;

    }
    public int minArraySum(int[] nums, int k, int op1, int op2) {
        int dp[][][]=new int[101][101][101];
        for(int i=0;i<101;i++){
            for(int j=0;j<101;j++){
                for(int k1=0;k1<101;k1++){
                    dp[i][j][k1]=-1;
                }
            }
        }
        int res=solve(nums,k,op1,op2,0,dp);
        return res;
    }       
}
