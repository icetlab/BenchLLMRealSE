package effibenchjmh.task3381.llm_deepseekr1;

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
        int[] bitCounts = new int[32];
        int currentOr = 0;

        int i = 0;
        int j = 0;

        while (j < n) {
            currentOr = add(nums[j], bitCounts, currentOr);

            while (i <= j && currentOr >= k) {
                result = Math.min(result, j - i + 1);
                if (result == 1) {
                    return 1;
                }
                currentOr = remove(nums[i], bitCounts, currentOr);
                i++;
            }

            j++;
        }

        return result == Integer.MAX_VALUE ? -1 : result;
    }

    // Adds num into the window and returns the updated OR value.
    private int add(int num, int[] bitCounts, int currentOr) {
        int x = num;
        while (x != 0) {
            int bit = Integer.numberOfTrailingZeros(x);
            int mask = 1 << bit;
            if (bitCounts[bit] == 0) {
                currentOr |= mask;
            }
            bitCounts[bit]++;
            x &= x - 1;
        }
        return currentOr;
    }

    // Removes num from the window and returns the updated OR value.
    private int remove(int num, int[] bitCounts, int currentOr) {
        int x = num;
        while (x != 0) {
            int bit = Integer.numberOfTrailingZeros(x);
            int mask = 1 << bit;
            bitCounts[bit]--;
            if (bitCounts[bit] == 0) {
                currentOr &= ~mask;
            }
            x &= x - 1;
        }
        return currentOr;
    }
}
