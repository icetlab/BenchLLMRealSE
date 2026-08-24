package effibenchjmh.task3442;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
  public int maxTotalReward(int[] rewardValues) {
    Arrays.sort(rewardValues);

    int n = rewardValues.length;
    int[][] memo = new int[n][4000];
    for (int i = 0; i < n; i++) {
      Arrays.fill(memo[i], -1);
    }

    return dfs(rewardValues, 0, 0, memo);
  }

  private static int dfs(int[] rewardValues, int i, int currX, int[][] memo) {
    if (i == rewardValues.length) {
      return currX;
    }
    if (memo[i][currX] != -1) {
      return memo[i][currX];
    }

    int pick = (rewardValues[i] > currX) ? dfs(rewardValues, i + 1, rewardValues[i] + currX, memo) : 0;
    int notPick = dfs(rewardValues, i + 1, currX, memo);

    return memo[i][currX] = Math.max(pick, notPick);

  }
}
