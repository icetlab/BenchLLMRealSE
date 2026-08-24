package effibenchjmh.task3442.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    Integer dp[][];
    public int maxTotalReward(int[] arr) {
        Arrays.sort(arr);
        int sum = 0;
        for(int i : arr) sum += i;
        dp = new Integer[arr.length][10000];
        return util(arr, 0, 0);
    }
    private int util(int arr[], int i, int sum) {
        if(i >= arr.length) return sum;
        if(dp[i][sum] != null) return dp[i][sum];
        if(sum < arr[i]) {
            return dp[i][sum] = Math.max(util(arr, i+1, sum + arr[i]), util(arr, i+1, sum));
        }
        return dp[i][sum] = util(arr, i+1, sum);
    }
}
