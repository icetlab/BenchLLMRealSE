package effibenchjmh.task3644;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int minimumSumSubarray(List<Integer> nums, int l, int r) {
        int n = nums.size();
        int[] pre = new int[n];
        for (int i = 0; i < n; i++) {
            int res = (i == 0 ? 0 : pre[i - 1]) + nums.get(i);
            pre[i] = res;
        }
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int best = Integer.MAX_VALUE;
        int count = 0;
        for (int i = l - 1; i < n; i++) {
            int toAdd = i >= l ? pre[i - l] : 0;
            map.put(toAdd, map.getOrDefault(toAdd, 0) + 1);
            count++;
            if (count > r - l + 1) {
                int toRemove = i > r ? pre[i - r - 1] : 0;
                map.put(toRemove, map.get(toRemove) - 1);
                if (map.get(toRemove) == 0) map.remove(toRemove);
                count--;
            }
            Integer closest = map.lowerKey(pre[i]);
            if (closest != null) {
                best = Math.min(best, pre[i] - closest);
            }
        }
        return best == Integer.MAX_VALUE ? -1 : best;
    }
}
