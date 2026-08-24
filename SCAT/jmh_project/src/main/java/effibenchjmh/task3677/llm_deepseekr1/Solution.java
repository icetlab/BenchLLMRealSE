package effibenchjmh.task3677.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    int dp[][][];
    boolean visited[][][];
    int rows, cols;
    int coins[][];
    public int maximumAmount(int[][] coins) {
        int m = coins.length;
        int n = coins[0].length;
        final int NEG = Integer.MIN_VALUE + 2000;

        int[][] next = new int[n][3];
        int[][] cur = new int[n][3];

        for (int i = m - 1; i >= 0; i--) {
            boolean canDown = i + 1 < m;

            for (int j = n - 1; j >= 0; j--) {
                int c = coins[i][j];

                if (i == m - 1 && j == n - 1) {
                    for (int k = 0; k <= 2; k++) {
                        if (c >= 0) {
                            cur[j][k] = c;
                        } else {
                            cur[j][k] = (k > 0) ? 0 : c;
                        }
                    }
                } else {
                    boolean canRight = j + 1 < n;

                    for (int k = 0; k <= 2; k++) {
                        int down = canDown ? next[j][k] : NEG;
                        int right = canRight ? cur[j + 1][k] : NEG;

                        if (c >= 0) {
                            cur[j][k] = Math.max(down, right) + c;
                        } else {
                            int takeDown = down + c;
                            int takeRight = right + c;
                            int skipDown = (canDown && k > 0) ? next[j][k - 1] : NEG;
                            int skipRight = (canRight && k > 0) ? cur[j + 1][k - 1] : NEG;
                            cur[j][k] = Math.max(Math.max(takeDown, takeRight),
                                                 Math.max(skipDown, skipRight));
                        }
                    }
                }
            }

            int[][] temp = next;
            next = cur;
            cur = temp;
        }

        return next[0][2];
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
