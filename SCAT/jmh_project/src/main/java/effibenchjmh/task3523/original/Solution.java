package effibenchjmh.task3523.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int[] resultsArray(int[] nums, int k) {
        int[] dp=new int[nums.length];
        Arrays.fill(dp,1);
        int count=1;
        for(int i=1;i<nums.length;i++)
        {
            if(nums[i]>nums[i-1] && 1+nums[i-1]==nums[i])
            {
                count++;
                dp[i]=count;
            }
            else
            {
                count=1;
                dp[i]=1;
            }
        }
        System.out.println(Arrays.toString(dp));
        int f=0;
        int[] ans=new int[nums.length-k+1];
        for(int i=k-1;i<nums.length;i++)
        {
            if(dp[i]>=k)
            {
                ans[f]=nums[i];
            }
            else ans[f]=-1;
            f++;
        }
        return ans;
    }
}
