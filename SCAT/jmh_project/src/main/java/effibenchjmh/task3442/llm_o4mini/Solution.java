package effibenchjmh.task3442.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int maxTotalReward(int[] arr) {
        java.util.Arrays.sort(arr);
        int totalSum = 0;
        for (int v : arr) totalSum += v;
        boolean[] dp = new boolean[totalSum + 1];
        dp[0] = true;
        int max = 0;
        for (int val : arr) {
            for (int s = max; s >= 0; s--) {
                if (dp[s] && val > s) {
                    dp[s + val] = true;
                    if (s + val > max) max = s + val;
                }
            }
        }
        return max;
    }
}
