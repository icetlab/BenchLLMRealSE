package effibenchjmh.task3620.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    public int maxDistinctElements(int[] nums, int k) {
        List<int[]> ranges = new ArrayList<>();
        for (int num : nums) {
            ranges.add(new int[]{num - k, num + k});
        }
        ranges.sort(Comparator.comparingInt(a -> a[0]));
        Set<Integer> selected = new HashSet<>();
        int cnt = Integer.MIN_VALUE; 
        for (int[] range : ranges) {
            int start = range[0], end = range[1];
            int val = Math.max(cnt, start);
            if (val <= end && (selected.isEmpty() || !selected.contains(val))) {
                selected.add(val);
                cnt = val + 1; 
            }
        }
        return selected.size();
    }
}
