package effibenchjmh.task3677.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    int dp[][][];
    boolean visited[][][];
    int rows, cols;
    int coins[][];
    public int maximumAmount(int[][] coins) {
        this.rows = coins.length;
        this.cols = coins[0].length;
        this.dp = new int[rows][cols][3];
        this.visited = new boolean[rows][cols][3];
        this.coins = coins;
        helper(0, 0, 2);
        return dp[0][0][2];
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
