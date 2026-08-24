package effibenchjmh.task3291.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public boolean canSortArray(int[] nums) {
        int n = nums.length;
        
        // Group elements by their bit count and sort each group
        int i = 0;
        while (i < n) {
            int j = i;
            int bitCount = Integer.bitCount(nums[i]);
            
            // Find the segment with same bit count
            while (j < n && Integer.bitCount(nums[j]) == bitCount) {
                j++;
            }
            
            // Sort the segment
            for (int k = i; k < j; k++) {
                for (int l = k + 1; l < j; l++) {
                    if (nums[k] > nums[l]) {
                        int temp = nums[k];
                        nums[k] = nums[l];
                        nums[l] = temp;
                    }
                }
            }
            
            i = j;
        }
        
        // Check if array is sorted
        for (int k = 0; k < n - 1; k++) {
            if (nums[k] > nums[k + 1]) {
                return false;
            }
        }
        
        return true;
    }
}
