package effibenchjmh.task3248.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public long incremovableSubarrayCount(int[] nums) {
        int n = nums.length;
        int p = n - 1;
        // Find the start of the longest strictly increasing suffix
        while (p > 0 && nums[p - 1] < nums[p]) {
            p--;
        }

        // If the whole array is strictly increasing, any subarray removal is valid.
        if (p == 0) {
            return (long) n * (n + 1) / 2;
        }

        // ans is initialized with removals that leave only a suffix.
        // 1 for removing the whole array (leaving an empty suffix).
        // (n - p) for removing a prefix, leaving one of the (n-p) non-empty strictly increasing suffixes.
        long ans = 1 + (n - p);

        // Iterate through possible strictly increasing prefixes nums[0...i]
        for (int i = 0; i < p; i++) {
            // Break if the prefix is no longer strictly increasing
            if (i > 0 && nums[i] <= nums[i - 1]) {
                break;
            }

            // Find the first index k in [p, n-1] where nums[k] > nums[i]
            int k = upperBound(nums, p, nums[i]);
            
            // (n - k) is the number of valid suffixes we can connect to.
            // +1 is for the case where we leave just the prefix.
            ans += (n - k) + 1;
        }
        return ans;
    }

    private int upperBound(int[] arr, int start, int target) {
        int left = start, right = arr.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] > target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
}
