package effibenchjmh.task3594.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int getLargestOutlier(int[] nums) {
        long sum = 0;
        Map<Integer, Integer> counts = new HashMap<>();
        for (int num : nums) {
            sum += num;
            counts.put(num, counts.getOrDefault(num, 0) + 1);
        }

        int result = Integer.MIN_VALUE;
        for (int num : counts.keySet()) {
            if (num <= result) {
                continue;
            }
            long s = sum - num;
            if (s % 2 == 0) {
                int half = (int) (s / 2);
                if (counts.containsKey(half)) {
                    if (num != half || counts.get(half) > 1) {
                        result = Math.max(result, num);
                    }
                }
            }
        }

        return result;
    }
}
