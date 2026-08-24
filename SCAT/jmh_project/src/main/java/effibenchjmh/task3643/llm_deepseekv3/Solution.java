package effibenchjmh.task3643.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int minZeroArray(int[] nums, int[][] queries) {
        int left = 0, right = queries.length;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (canMakeZero(nums, queries, mid)) {
                result = mid;
                right = mid - 1; // try smaller k
            } else {
                left = mid + 1; // need more queries
            }
        }

        return result;
    }

    private boolean canMakeZero(int[] nums, int[][] queries, int k) {
        int n = nums.length;
        long[] diff = new long[n + 1]; // Difference array

        for (int i = 0; i < k; i++) {
            int l = queries[i][0];
            int r = queries[i][1];
            int val = queries[i][2];

            diff[l] += val;
            if (r + 1 < n) {
                diff[r + 1] -= val;
            }
        }

        long current = 0;
        for (int i = 0; i < n; i++) {
            current += diff[i];
            if (nums[i] > current) {
                return false;
            }
        }

        return true;
    }
}
