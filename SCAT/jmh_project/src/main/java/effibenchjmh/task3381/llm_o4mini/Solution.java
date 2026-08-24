package effibenchjmh.task3381.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int minimumSubarrayLength(int[] nums, int k) {
        int n = nums.length;
        int result = Integer.MAX_VALUE;

        int i = 0;
        int j = 0;

        //Initialize primitive counts and rolling OR
        int[] counts = new int[32];
        int currentOr = 0;

        //T.C : O(2 * n)
        while (j < n) {
            int num = nums[j];
            currentOr |= num;
            for (int b = 0; b < 32; b++) {
                if (((num >> b) & 1) == 1) {
                    counts[b]++;
                }
            }
            while (i <= j && currentOr >= k) {
                result = Math.min(result, j - i + 1);
                int rem = nums[i];
                for (int b = 0; b < 32; b++) {
                    if (((rem >> b) & 1) == 1) {
                        counts[b]--;
                        if (counts[b] == 0) {
                            currentOr &= ~(1 << b);
                        }
                    }
                }
                i++;
            }
            j++;
        }

        return result == Integer.MAX_VALUE ? -1 : result;
    }




}
