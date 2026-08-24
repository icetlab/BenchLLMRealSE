package effibenchjmh.task3263.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int minimumCost(int[] nums) {
        int first = nums[0];
        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;
        for (int i = 1; i < nums.length; i++) {
            int val = nums[i];
            if (val < min1) {
                min2 = min1;
                min1 = val;
            } else if (val < min2) {
                min2 = val;
            }
        }
        return first + min1 + min2;
    }
}
