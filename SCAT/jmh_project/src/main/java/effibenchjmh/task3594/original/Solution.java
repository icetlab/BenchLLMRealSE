package effibenchjmh.task3594.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int getLargestOutlier(int[] nums) {
        int sum = 0;
        Map<Integer, Set<Integer>> num2indices = new HashMap<>();
        for (int i = 0; i < nums.length; i ++) {
            sum += nums[i];
            Set<Integer> set = num2indices.getOrDefault(nums[i], new HashSet<Integer>());
            set.add(i);
            num2indices.put(nums[i], set);
        }

        int result = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i ++) {
            if (nums[i] <= result) {
                continue;
            }
            int s = sum - nums[i];
            if (s % 2 == 0) {
                int half = s / 2;
                if (num2indices.containsKey(half)) {
                    Set<Integer> set = num2indices.get(half);
                    if (!set.contains(i) || (set.contains(i) && set.size() > 1)) {
                        result = Math.max(result, nums[i]);
                    }
                }
            }
        }

        return result;
    }
}
