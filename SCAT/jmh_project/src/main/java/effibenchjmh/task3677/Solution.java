package effibenchjmh.task3677;

import java.util.*;
import java.util.stream.*;

class Solution {
    public int maximumAmount(int[][] coins) {
        int m = coins.length, n = coins[0].length;
        int INF = Integer.MIN_VALUE;
        int[][][] dp = new int[m][n][3];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j][0] = INF;
                dp[i][j][1] = INF;
                dp[i][j][2] = INF;
            }
        }
        if (coins[0][0] >= 0) {
            dp[0][0][0] = coins[0][0];
        } else {
            dp[0][0][0] = coins[0][0];
            dp[0][0][1] = 0;
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int used = 0; used < 3; used++) {
                    if (dp[i][j][used] == INF)
                        continue;
                    if (j + 1 < n) {
                        int coinVal = coins[i][j + 1];
                        if (coinVal >= 0) {
                            int newVal = dp[i][j][used] + coinVal;
                            dp[i][j + 1][used] = Math.max(dp[i][j + 1][used], newVal);
                        } else {
                            int newVal = dp[i][j][used] + coinVal;
                            dp[i][j + 1][used] = Math.max(dp[i][j + 1][used], newVal);
                            if (used < 2) {
                                newVal = dp[i][j][used];
                                dp[i][j + 1][used + 1] = Math.max(dp[i][j + 1][used + 1], newVal);
                            }
                        }
                    }
                    if (i + 1 < m) {
                        int coinVal = coins[i + 1][j];
                        if (coinVal >= 0) {
                            int newVal = dp[i][j][used] + coinVal;
                            dp[i + 1][j][used] = Math.max(dp[i + 1][j][used], newVal);
                        } else {
                            int newVal = dp[i][j][used] + coinVal;
                            dp[i + 1][j][used] = Math.max(dp[i + 1][j][used], newVal);
                            if (used < 2) {
                                newVal = dp[i][j][used];
                                dp[i + 1][j][used + 1] = Math.max(dp[i + 1][j][used + 1], newVal);
                            }
                        }
                    }
                }
            }
        }
        int ans = INF;
        for (int used = 0; used < 3; used++) {
            ans = Math.max(ans, dp[m - 1][n - 1][used]);
        }
        return ans;
    }
}
