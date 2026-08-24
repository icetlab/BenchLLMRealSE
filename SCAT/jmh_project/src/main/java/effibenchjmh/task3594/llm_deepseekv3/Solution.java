package effibenchjmh.task3594.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int getLargestOutlier(int[] nums) {
        int sum = 0;
        Map<Integer, Integer> count = new HashMap<>();
        for (int num : nums) {
            sum += num;
            count.put(num, count.getOrDefault(num, 0) + 1);
        }

        int result = Integer.MIN_VALUE;
        for (int num : nums) {
            if (num <= result) {
                continue;
            }
            int s = sum - num;
            if (s % 2 == 0) {
                int half = s / 2;
                int cnt = count.getOrDefault(half, 0);
                if (cnt > 0 && (half != num || cnt > 1)) {
                    result = num;
                }
            }
        }

        return result;
    }
}
