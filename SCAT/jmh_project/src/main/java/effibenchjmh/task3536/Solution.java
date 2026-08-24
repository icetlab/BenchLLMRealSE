package effibenchjmh.task3536;

import java.util.*;
import java.util.stream.*;

class Solution {
    //to check whether its a non decreasing array if not whether its possible to convert it
    //after transforming a array into non decreasing array it will return minimum element
    
    public int f1(int[] nums){
        int la=nums[0];
        int min=nums[0];
        for(int i=1;i<nums.length;i++){
            if(nums[i]<la){
                if(la-nums[i]<=min){
                    min=min-(la-nums[i]);
                }
                else return -1;
            }
            la=nums[i];
        }
        return min;
    }
    public int countOfPairs(int[] nums) {
        int t=f1(nums);

        if(t==-1)
            return 0;  //no pair exist
        long res=1;
        if(t==0)
            return 1;  //only {0,0,....,0,0} is one valid pair possible
        int mod=1000000007;
        long[] arr=new long[nums.length]; //to maintain prefix sum
        Arrays.fill(arr,1);
        for(int i=1;i<=t;i++){
            long sum=0;
            for(int j=nums.length-1;j>=0;j--){
                sum+=arr[j];
                arr[j]=sum%mod;
            }
            res=(res+sum)%mod;
        }
        return (int)res%mod;
    }
}
