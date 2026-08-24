package effibenchjmh.task3291.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public boolean canSortArray(int[] nums) {
                int n = nums.length;
        if (n <= 1) {
            return true;
        }

        int prevBlockMax = 0;
        int i = 0;
        while (i < n) {
            int currentBitCount = Integer.bitCount(nums[i]);
            int currentBlockMin = nums[i];
            int currentBlockMax = nums[i];
            
            int j = i + 1;
            while (j < n && Integer.bitCount(nums[j]) == currentBitCount) {
                currentBlockMin = Math.min(currentBlockMin, nums[j]);
                currentBlockMax = Math.max(currentBlockMax, nums[j]);
                j++;
            }
            
            if (currentBlockMin < prevBlockMax) {
                return false;
            }
            
            prevBlockMax = currentBlockMax;
            i = j;
        }
        
        return true;

    }
}
