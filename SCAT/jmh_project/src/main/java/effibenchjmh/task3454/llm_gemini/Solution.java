package effibenchjmh.task3454.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public long minimumOperations(int[] nums, int[] target) {
        long totalIncrements = 0;
        long prev_delta = 0;

        for (int i = 0; i <= nums.length; i++) {
            long current_delta = (i == nums.length) ? 0 : (long)target[i] - nums[i];
            if (current_delta > prev_delta) {
                totalIncrements += current_delta - prev_delta;
            }
            prev_delta = current_delta;
        }

        return totalIncrements;
    }
}
