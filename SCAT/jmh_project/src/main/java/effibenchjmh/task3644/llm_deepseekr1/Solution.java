package effibenchjmh.task3644.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int minimumSumSubarray(List<Integer> nums, int l, int r) {
    int n = nums.size();
    if (l < 1) l = 1;
    if (l > r) return -1;

    int[] prefix = new int[n + 1];
    for (int i = 0; i < n; i++) {
        prefix[i + 1] = prefix[i] + nums.get(i);
    }

    java.util.TreeMap<Integer, Integer> active = new java.util.TreeMap<Integer, Integer>();
    int res = -1;

    for (int end = 0; end < n; end++) {
        int startToAdd = end - l + 1;
        if (startToAdd >= 0) {
            Integer cur = active.get(prefix[startToAdd]);
            active.put(prefix[startToAdd], cur == null ? 1 : cur + 1);
        }

        int startToRemove = end - r;
        if (startToRemove >= 0) {
            int count = active.get(prefix[startToRemove]);
            if (count == 1) {
                active.remove(prefix[startToRemove]);
            } else {
                active.put(prefix[startToRemove], count - 1);
            }
        }

        Integer lower = active.lowerKey(prefix[end + 1]);
        if (lower != null) {
            int sum = prefix[end + 1] - lower;
            if (res == -1 || sum < res) {
                res = sum;
            }
        }
    }

    return res;
    }
}
