package effibenchjmh.task3443.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

import java.util.Arrays;

class Solution {
    public int maxTotalReward(int[] rewardValues) {
        int[] nums = Arrays.stream(rewardValues).distinct().sorted().toArray();
        int max = nums[nums.length - 1];
        int limit = 2 * max; // maximum possible sum is < 2*max
        boolean[] dp = new boolean[limit];
        dp[0] = true;
        for (int v : nums) {
            for (int s = v - 1; s >= 0; s--) {
                if (dp[s]) {
                    dp[s + v] = true;
                }
            }
        }
        for (int s = limit - 1; s >= 0; s--) {
            if (dp[s]) return s;
        }
        return 0;
    }
}
