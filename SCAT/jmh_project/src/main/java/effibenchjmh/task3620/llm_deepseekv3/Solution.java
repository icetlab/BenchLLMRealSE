package effibenchjmh.task3620.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int maxDistinctElements(int[] nums, int k) {
        Arrays.sort(nums);
        int count = 0;
        int nextVal = Integer.MIN_VALUE;
        for (int num : nums) {
            int start = num - k;
            int end = num + k;
            int val = Math.max(nextVal, start);
            if (val <= end) {
                count++;
                nextVal = val + 1;
            }
        }
        return count;
    }
}
