package effibenchjmh.task3291.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    public boolean canSortArray(int[] nums) {
        int c=0;
        for(int i=0;i<nums.length-1;i++)
        {
            if(nums[i]>nums[i+1])
            {
                int a=Integer.bitCount(nums[i]);
                int b=Integer.bitCount(nums[i+1]);
                System.out.println(a+" "+b);
                if(a==b)
                {
                    int t=nums[i+1];
                nums[i+1]=nums[i];
                nums[i]=t;
                }
                else
                return false;
            }
        }
         for(int i=nums.length-1;i>0;i--)
        {
            if(nums[i]<nums[i-1])
            {
                int a=Integer.bitCount(nums[i]);
                int b=Integer.bitCount(nums[i-1]);
                //System.out.println(a+" "+b);
                if(a==b)
                {
                    int t=nums[i-1];
                nums[i-1]=nums[i];
                nums[i]=t;
                }
                else
                return false;
            }
        }
        return true;

    }
}
