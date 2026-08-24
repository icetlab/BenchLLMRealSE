package effibenchjmh.task3677.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int maximumAmount(int[][] coins) {
        int rows = coins.length;
        int cols = coins[0].length;
        int[][][] dp = new int[rows][cols][3];
        
        // Initialize with very small values
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int k = 0; k < 3; k++) {
                    dp[i][j][k] = Integer.MIN_VALUE + 2000;
                }
            }
        }
        
        // Base case: bottom-right cell
        for (int k = 0; k < 3; k++) {
            if (coins[rows-1][cols-1] >= 0) {
                dp[rows-1][cols-1][k] = coins[rows-1][cols-1];
            } else if (k > 0) {
                dp[rows-1][cols-1][k] = 0;
            } else {
                dp[rows-1][cols-1][k] = coins[rows-1][cols-1];
            }
        }
        
        // Fill from bottom-right to top-left
        for (int i = rows-1; i >= 0; i--) {
            for (int j = cols-1; j >= 0; j--) {
                if (i == rows-1 && j == cols-1) continue;
                for (int k = 0; k < 3; k++) {
                    int best = Integer.MIN_VALUE + 2000;
                    // Move down
                    if (i+1 < rows) {
                        best = Math.max(best, dp[i+1][j][k]);
                    }
                    // Move right
                    if (j+1 < cols) {
                        best = Math.max(best, dp[i][j+1][k]);
                    }
                    
                    if (coins[i][j] >= 0) {
                        if (best != Integer.MIN_VALUE + 2000) {
                            dp[i][j][k] = best + coins[i][j];
                        }
                    } else {
                        // Option 1: take the negative coin
                        if (best != Integer.MIN_VALUE + 2000) {
                            dp[i][j][k] = Math.max(dp[i][j][k], best + coins[i][j]);
                        }
                        // Option 2: neutralize the negative coin (use one neutralization)
                        if (k > 0) {
                            int bestNeutral = Integer.MIN_VALUE + 2000;
                            if (i+1 < rows) {
                                bestNeutral = Math.max(bestNeutral, dp[i+1][j][k-1]);
                            }
                            if (j+1 < cols) {
                                bestNeutral = Math.max(bestNeutral, dp[i][j+1][k-1]);
                            }
                            if (bestNeutral != Integer.MIN_VALUE + 2000) {
                                dp[i][j][k] = Math.max(dp[i][j][k], bestNeutral);
                            }
                        }
                    }
                }
            }
        }
        
        return dp[0][0][2];
    }
}
