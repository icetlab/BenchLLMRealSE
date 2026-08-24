package effibenchjmh.task3522.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int[] resultsArray(int[] nums, int k) {
        return resultsArrayOptimized(nums, k);
    }

    private int[] resultsArrayOptimized(int[] nums, int k) {
        if (k == 1) {
            return nums;
        }
        int n = nums.length;
        int[] res = new int[n - k + 1];
        int run = 0;

        for (int i = 0; i < n; i++) {
            if (i > 0) {
                if ((long) nums[i] - nums[i - 1] == 1) {
                    run++;
                } else {
                    run = 0;
                }
            }
            if (i >= k - 1) {
                res[i - k + 1] = (run >= k - 1) ? nums[i] : -1;
            }
        }

        return res;
    }

    private int[] resultsArrayOld(int[] nums, int k) {
        
        int res[]=new int[nums.length-k+1];
        if(k==1)
        {
            return nums;
        }
        for(int i=0;i<res.length;i++)
        {
            int num=0;
            int temp[]=new int[k];
            int ind=0;
            
            boolean diff=false;
            
            for(int j=i;j<i+k;j++)
            {
                temp[ind++]=nums[j];
            
                if((j+1)<i+k && (nums[j]-nums[j+1]!=-1))    
                {
                    diff=true;
                }
                
                
            }
            
            java.util.Arrays.sort(temp);
            ind=0;
            
            boolean flag=true;
            for(int j=i;j<i+k;j++)
            {
                if(nums[j]!=temp[ind++])
                {
                    flag=false;
                    break;
                }

                
            }
            
            if(flag && !diff)
            {
                res[i]=temp[k-1];
            }
            else
                res[i]=-1;
            
        }

        
        return res;
    }
}
