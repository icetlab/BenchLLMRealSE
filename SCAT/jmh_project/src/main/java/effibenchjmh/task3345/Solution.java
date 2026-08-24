package effibenchjmh.task3345;

import java.util.*;
import java.util.stream.*;

class Solution {
    public int sumOfPower(int[] nums, int target) {
        // Define MOD constant
        final int MOD = 1000000007;
        
        // Get the size of the nums array
        int size = nums.length;
        
        // Initialize a 2D array to store the dynamic programming results
        int[][] dp = new int[size + 1][target + 1];
        
        // Initialize dp[0][0] to 1, as there is one way to get a sum of 0 with an empty subset
        dp[0][0] = 1;

        // Perform dynamic programming
        for (int i = 1; i <= size; ++i) {
            for (int j = 0; j <= target; ++j) {
                // Calculate the number of ways to get the sum j without using the current number
                dp[i][j] = (dp[i - 1][j] * 2) % MOD;
                
                // Calculate the number of ways to get the sum j using the current number
                int diff = j - nums[i - 1];
                if (diff >= 0) {
                    dp[i][j] = (dp[i][j] + dp[i - 1][diff]) % MOD;
                }
            }
        }

        // Return the result stored in dp[size][target]
        return dp[size][target];
    }
}
