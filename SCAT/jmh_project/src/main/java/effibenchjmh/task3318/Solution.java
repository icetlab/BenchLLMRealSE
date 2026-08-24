package effibenchjmh.task3318;

import java.util.*;
import java.util.stream.*;

class Solution {
    public int maxOperations(int[] nums) {
        int maxOperations = 0;
        int len = nums.length;
        int[][] memoization = new int[len][len];
        int start = 0;
        int end = len - 1;

        maxOperations = Math.max(maxOperations,
                findMaxOpsHelper(nums, start + 2, end, nums[start] + nums[start + 1], memoization) + 1);
        maxOperations = Math.max(maxOperations,
                findMaxOpsHelper(nums, start + 1, end - 1, nums[start] + nums[end], memoization) + 1);
        maxOperations = Math.max(maxOperations,
                findMaxOpsHelper(nums, start, end - 2, nums[end] + nums[end - 1], memoization) + 1);

        return maxOperations;
    }

    private int findMaxOpsHelper(int[] nums, int left, int right, int previousSum, int[][] memoization) {
        if (left >= right)
            return 0;
        if (memoization[left][right] != 0)
            return memoization[left][right];

        int maxOps = 0;
        if (nums[left] + nums[left + 1] == previousSum)
            maxOps = Math.max(maxOps, findMaxOpsHelper(nums, left + 2, right, previousSum, memoization) + 1);
        if (nums[left] + nums[right] == previousSum)
            maxOps = Math.max(maxOps, findMaxOpsHelper(nums, left + 1, right - 1, previousSum, memoization) + 1);
        if (nums[right] + nums[right - 1] == previousSum)
            maxOps = Math.max(maxOps, findMaxOpsHelper(nums, left, right - 2, previousSum, memoization) + 1);

        return memoization[left][right] = maxOps;
    }
}
