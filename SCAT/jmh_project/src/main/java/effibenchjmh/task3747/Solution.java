package effibenchjmh.task3747;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int maxAdjacentDistance(int[] nums) {
        int max = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int next = (i + 1) % n;
            int diff = Math.abs(nums[i] - nums[next]);
            if (diff > max) {
                max = diff;
            }
        }
        return max;
    }
}
