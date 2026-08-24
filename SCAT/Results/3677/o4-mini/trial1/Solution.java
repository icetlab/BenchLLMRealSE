class Solution {
    int dp[][][];
    boolean visited[][][];
    int rows, cols;
    int coins[][];
    public int maximumAmount(int[][] coins) {
        int m = coins.length;
        int n = coins[0].length;
        int K = 2;
        int NEG_INF = Integer.MIN_VALUE/2;
        int[][][] dp = new int[m+1][n+1][K+1];
        // initialize out-of-bounds to NEG_INF
        for (int i = 0; i <= m; i++) {
            for (int k = 0; k <= K; k++) {
                dp[i][n][k] = NEG_INF;
            }
        }
        for (int j = 0; j <= n; j++) {
            for (int k = 0; k <= K; k++) {
                dp[m][j][k] = NEG_INF;
            }
        }
        // base case at bottom-right
        for (int k = 0; k <= K; k++) {
            if (coins[m-1][n-1] >= 0) dp[m-1][n-1][k] = coins[m-1][n-1];
            else if (k > 0) dp[m-1][n-1][k] = 0;
            else dp[m-1][n-1][k] = coins[m-1][n-1];
        }
        // fill dp
        for (int i = m-1; i >= 0; i--) {
            for (int j = n-1; j >= 0; j--) {
                if (i == m-1 && j == n-1) continue;
                for (int k = 0; k <= K; k++) {
                    int coin = coins[i][j];
                    if (coin >= 0) {
                        int best = Math.max(dp[i+1][j][k], dp[i][j+1][k]);
                        dp[i][j][k] = best > NEG_INF ? best + coin : NEG_INF;
                    } else {
                        int val1 = dp[i+1][j][k] + coin;
                        int val2 = dp[i][j+1][k] + coin;
                        int val3 = k > 0 ? dp[i+1][j][k-1] : NEG_INF;
                        int val4 = k > 0 ? dp[i][j+1][k-1] : NEG_INF;
                        dp[i][j][k] = Math.max(Math.max(val1, val2), Math.max(val3, val4));
                    }
                }
            }
        }
        return dp[0][0][K];
    }
    public int helper(int row, int col, int k){
        if(row>=this.rows || col>=this.cols || k<0) return Integer.MIN_VALUE+2000;

        
        if(row==this.rows-1 && col==this.cols-1){
            if(this.coins[row][col]>=0) dp[row][col][k] = this.coins[row][col];
            else if(k>0) dp[row][col][k] = 0;
            else dp[row][col][k] = coins[row][col];
            return dp[row][col][k];
        }

        if(visited[row][col][k]) return dp[row][col][k];
        this.visited[row][col][k] = true;
        if(coins[row][col]>=0){
            this.dp[row][col][k] = Math.max(helper(row+1, col, k), helper(row, col+1, k)) + coins[row][col];
        }else{
            int val1 = helper(row+1, col, k)+coins[row][col];
            int val2 = helper(row, col+1, k)+coins[row][col];
            int val3 = helper(row+1, col, k-1);
            int val4 = helper(row, col+1, k-1);
            this.dp[row][col][k] = Math.max(Math.max(val1, val2), Math.max(val3, val4));
        }
        return this.dp[row][col][k];
    }
}