package effibenchjmh.task3248.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    public long incremovableSubarrayCount(int[] nums) {
        List<Integer> lastInc = new ArrayList<>();
        lastInc.add(nums[nums.length - 1]);
        for (int i = nums.length - 2; i >= 0; --i) {  // find the increasing sequence from the right most side
            if (nums[i] >= nums[i + 1]) break;
            lastInc.add(nums[i]);
        }
        Collections.reverse(lastInc);  // reverse it to set it to ascending order
        if (nums.length == lastInc.size()) return ((long) nums.length * (nums.length + 1) / 2);  // If the complete array is in increasing then total no of way we can remove = total number of subarray possible = (n*(n+1))/2

        long ans = 1 + lastInc.size();
        int n = nums.length - lastInc.size();
        int last = -1;
        for (int i = 0; i < n; ++i) {
            if (nums[i] <= last) break;
            last = nums[i];
            ans += lastInc.size() - upperBoundII2972(lastInc, nums[i]) + 1;
        }
        return ans;
    }

    private int upperBoundII2972(List<Integer> list, int target) {
        int left = 0, right = list.size();
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) > target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
}
