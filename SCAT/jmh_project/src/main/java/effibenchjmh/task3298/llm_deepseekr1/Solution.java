package effibenchjmh.task3298.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

import java.util.Arrays;

class Solution {
    public int maxSelectedElements(int[] nums) {
         Arrays.sort(nums);

        int n = nums.length;
        if (n == 0) {
            return 0;
        }

        // Only allocate up to the largest value + 2 for the transitions.
        int maxVal = nums[n - 1];
        int[] dp = new int[maxVal + 3];

        int ans = 0;
        for (int j = n - 1; j >= 0; j--) {
            int val = nums[j];

            dp[val] = Math.max(dp[val], 1 + dp[val + 1]);
            if (dp[val] > ans) ans = dp[val];

            dp[val + 1] = Math.max(dp[val + 1], 1 + dp[val + 2]);
            if (dp[val + 1] > ans) ans = dp[val + 1];
        }

        return ans;
    
    }
}
