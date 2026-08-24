package effibenchjmh.task3382;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    private int upperBound(List<Integer> nums, int x) {
        int start = 0, end = nums.size(), mid;
        while (start < end) {
            mid = (start + end) / 2;
            if (nums.get(mid) <= x) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        return start;
    }

    private int[] prevGreater(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        Arrays.fill(result, -1);
        Deque<Integer> s = new ArrayDeque<>();
        for (int i = n - 1; i >= 0; i--) {
            while (!s.isEmpty() && nums[i] > nums[s.peek()]) {
                result[s.peek()] = i;
                s.pop();
            }
            s.push(i);
        }
        return result;
    }

    public long numberOfSubarrays(int[] nums) {
        int n = nums.length;
        long result = 0;
        int[] pGreater = prevGreater(nums);
        HashMap<Integer, List<Integer>> indices = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (!indices.containsKey(nums[i])) {
                indices.put(nums[i], new ArrayList<>());
            }
            indices.get(nums[i]).add(i);
            result += indices.get(nums[i]).size() - upperBound(indices.get(nums[i]), pGreater[i]);
        }
        return result;
    }
}
