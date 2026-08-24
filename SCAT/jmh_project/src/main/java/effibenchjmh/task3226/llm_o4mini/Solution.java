package effibenchjmh.task3226.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int[] numberGame(int[] nums) {
        java.util.Arrays.sort(nums);
        int n = nums.length;
        int[] ans = new int[n];
        for (int k = 0; k < n; k += 2) {
            ans[k] = nums[k+1];
            ans[k+1] = nums[k];
        }
    return ans;
    }
}
