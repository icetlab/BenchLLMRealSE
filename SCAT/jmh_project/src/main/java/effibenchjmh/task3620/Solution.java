package effibenchjmh.task3620;

import java.util.*;
import java.util.stream.*;

class Solution {
    public int maxDistinctElements(int[] nums, int k) 
    {
      Arrays.sort(nums);
      int count = 0; 
      int max  = nums[0] - k;  
      for(int end = 0; end < nums.length;end++)
        { 
            max = Math.max(max,nums[end] - k);       
            if(max <= nums[end] + k )
            {
                count++;
                max++;
            } 
         
        }
      return count;
    }
}
