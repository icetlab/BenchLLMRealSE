package effibenchjmh.task3644.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int minimumSumSubarray(List<Integer> nums, int l, int r) 
    {
    int size = nums.size();

    // Use long for prefix sums to avoid potential integer overflow.
    long[] prefixSum = new long[size + 1];
    for (int i = 0; i < size; i++) {
        prefixSum[i + 1] = prefixSum[i] + nums.get(i);
    }

    long minSum = Long.MAX_VALUE;
    // TreeMap to store counts of prefix sums in the sliding window.
    java.util.TreeMap<Long, Integer> windowPrefixSums = new java.util.TreeMap<>();

    // j is the (exclusive) end index of the subarray.
    for (int j = 1; j <= size; j++) {
        // The window for the start index 'i' of the subarray is [j-r, j-l].
        // We update the window by adding prefixSum for the new start index and removing the one that's now too old.

        // Add the prefix sum for the newest possible start index (j-l).
        if (j >= l) {
            long valToAdd = prefixSum[j - l];
            windowPrefixSums.put(valToAdd, windowPrefixSums.getOrDefault(valToAdd, 0) + 1);
        }

        // Remove the prefix sum for the start index that just fell out of the window (j-r-1).
        if (j > r) {
            long valToRemove = prefixSum[j - r - 1];
            int count = windowPrefixSums.get(valToRemove);
            if (count == 1) {
                windowPrefixSums.remove(valToRemove);
            } else {
                windowPrefixSums.put(valToRemove, count - 1);
            }
        }

        // If a valid subarray can end at j-1, query for the minimum positive sum.
        if (j >= l && !windowPrefixSums.isEmpty()) {
            // We need to find max(prefixSum[i]) < prefixSum[j] to minimize the positive sum.
            Long bestPrefixSum = windowPrefixSums.lowerKey(prefixSum[j]);
            if (bestPrefixSum != null) {
                long sum = prefixSum[j] - bestPrefixSum;
                minSum = Math.min(minSum, sum);
            }
        }
    }

    return minSum == Long.MAX_VALUE ? -1 : (int) minSum;
    }
}
