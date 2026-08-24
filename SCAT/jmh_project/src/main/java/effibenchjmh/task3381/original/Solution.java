package effibenchjmh.task3381.original;

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

        //Initialize 'list' with 32 zeros to store bit counts
        List<Integer> list = new ArrayList<>(Collections.nCopies(32, 0));

        //T.C : O(2 * n)
        while (j < n) { // O(n)
            update(nums[j], list, 1); // Add nums[j] to the current window

            //Check if the current window has a sum >= k
            while (i <= j && getDecimalFromBinary(list) >= k) {
                result = Math.min(result, j - i + 1);
                update(nums[i], list, -1); // Remove nums[i] from the window
                i++;
            }

            j++;
        }

        return result == Integer.MAX_VALUE ? -1 : result;
    }

    //Updates the 'list' array based on the set bits in the number.
    private void update(int number, List<Integer> list, int val) {
        for (int i = 0; i < 32; i++) { // O(32)
            if ((number >> i & 1) == 1) {
                list.set(i, list.get(i) + val);
            }
        }
    }

    //Converts the binary representation stored in 'list' back to a decimal integer.
    private int getDecimalFromBinary(List<Integer> list) {
        int num = 0;
        for (int i = 0; i < 32; i++) { // O(32)
            if (list.get(i) > 0) { // ith position has a set bit
                num |= (1 << i);
            }
        }
        return num;
    }
}
