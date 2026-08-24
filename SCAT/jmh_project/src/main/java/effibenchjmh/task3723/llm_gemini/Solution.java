package effibenchjmh.task3723.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int sumOfGoodNumbers(int[] nums, int k) {
        int n = nums.length;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            // A number is "good" if it's strictly greater than all its existing neighbors at distance k,
            // and it must have at least one such neighbor.
            // The following condition checks for cases where a number is *not* good, and skips it.
            if ((i - k < 0 && i + k >= n) || // Case 1: No neighbors at distance k.
                (i - k >= 0 && nums[i] <= nums[i - k]) || // Case 2: Not greater than left neighbor.
                (i + k < n && nums[i] <= nums[i + k])) { // Case 3: Not greater than right neighbor.
                continue;
            }
            sum += nums[i];
        }
        return sum;
    }
}
