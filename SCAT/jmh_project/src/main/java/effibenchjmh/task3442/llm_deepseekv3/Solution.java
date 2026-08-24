package effibenchjmh.task3442.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

import java.util.*;

class Solution {
    public int maxTotalReward(int[] arr) {
        Arrays.sort(arr);
        int maxSum = 0;
        for (int v : arr) maxSum += v;
        boolean[] dp = new boolean[maxSum + 1];
        dp[0] = true;
        int currentMax = 0;
        for (int v : arr) {
            for (int s = currentMax; s >= 0; s--) {
                if (dp[s] && s < v) {
                    dp[s + v] = true;
                }
            }
            currentMax += v;
        }
        for (int s = maxSum; s >= 0; s--) {
            if (dp[s]) return s;
        }
        return 0;
    }
}
