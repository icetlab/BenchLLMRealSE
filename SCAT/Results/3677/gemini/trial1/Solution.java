class Solution {
    public int maximumAmount(int[][] coins) {
        int rows = coins.length;
        int cols = coins[0].length;

        // dp[r][c][k] = max amount from (r, c) to destination with k skips left.
        int[][][] dp = new int[rows][cols][3];
        
        // A value smaller than any possible path sum to represent an invalid path.
        final int UNREACHABLE = Integer.MIN_VALUE / 2;

        for (int r = rows - 1; r >= 0; r--) {
            for (int c = cols - 1; c >= 0; c--) {
                for (int k = 0; k <= 2; k++) {
                    // Base case: destination cell
                    if (r == rows - 1 && c == cols - 1) {
                        if (coins[r][c] >= 0) {
                            dp[r][c][k] = coins[r][c];
                        } else {
                            // If negative, skip if possible (k>0), otherwise must take.
                            dp[r][c][k] = (k > 0) ? 0 : coins[r][c];
                        }
                        continue;
                    }

                    // Max score from the next step (down or right)
                    int maxNextSameK = UNREACHABLE;
                    if (r + 1 < rows) {
                        maxNextSameK = Math.max(maxNextSameK, dp[r + 1][c][k]);
                    }
                    if (c + 1 < cols) {
                        maxNextSameK = Math.max(maxNextSameK, dp[r][c + 1][k]);
                    }

                    if (coins[r][c] >= 0) {
                        // Must take positive coin. If no path forward, this path is invalid.
                        dp[r][c][k] = (maxNextSameK == UNREACHABLE) ? UNREACHABLE : maxNextSameK + coins[r][c];
                    } else { // Negative coin
                        // Option 1: Take the coin
                        int take = (maxNextSameK == UNREACHABLE) ? UNREACHABLE : maxNextSameK + coins[r][c];

                        // Option 2: Skip the coin (if possible)
                        int skip = UNREACHABLE;
                        if (k > 0) {
                            int maxNextKMinus1 = UNREACHABLE;
                            if (r + 1 < rows) {
                                maxNextKMinus1 = Math.max(maxNextKMinus1, dp[r + 1][c][k - 1]);
                            }
                            if (c + 1 < cols) {
                                maxNextKMinus1 = Math.max(maxNextKMinus1, dp[r][c + 1][k - 1]);
                            }
                            if (maxNextKMinus1 != UNREACHABLE) {
                                skip = maxNextKMinus1;
                            }
                        }
                        
                        dp[r][c][k] = Math.max(take, skip);
                    }
                }
            }
        }
        
        return dp[0][0][2];
    }
}