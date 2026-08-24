package effibenchjmh.task3654.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int solve(int nums[],int k,int op1,int op2,int i,int dp[][][]){
        if (i >= nums.length) {
            return 0;
        }
        if (dp[i][op1][op2] != -1) {
            return dp[i][op1][op2];
        }

        int res = Integer.MAX_VALUE;
        int val = nums[i];
        int half = (val + 1) / 2;

        if (op1 > 0) {
            res = Math.min(res, half + solve(nums, k, op1 - 1, op2, i + 1, dp));
        }

        if (op2 > 0 && val >= k) {
            int reduced = val - k;
            res = Math.min(res, reduced + solve(nums, k, op1, op2 - 1, i + 1, dp));

            if (op1 > 0) {
                int h = half;
                if (h >= k) {
                    h -= k;
                }
                res = Math.min(res, h + solve(nums, k, op1 - 1, op2 - 1, i + 1, dp));
                res = Math.min(res, ((reduced + 1) / 2) + solve(nums, k, op1 - 1, op2 - 1, i + 1, dp));
            }
        }

        res = Math.min(res, val + solve(nums, k, op1, op2, i + 1, dp));
        return dp[i][op1][op2] = res;

    }
    public int minArraySum(int[] nums, int k, int op1, int op2) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }

        int[][] next = new int[op1 + 1][op2 + 1];

        for (int i = n - 1; i >= 0; i--) {
            int val = nums[i];
            int half = (val + 1) / 2;
            int[][] cur = new int[op1 + 1][op2 + 1];

            for (int a = 0; a <= op1; a++) {
                int[] nextA = next[a];
                int[] curA = cur[a];
                for (int b = 0; b <= op2; b++) {
                    int best = val + nextA[b];

                    if (a > 0) {
                        best = Math.min(best, half + next[a - 1][b]);
                    }

                    if (val >= k) {
                        int reduced = val - k;
                        if (b > 0) {
                            best = Math.min(best, reduced + nextA[b - 1]);

                            if (a > 0) {
                                int h = half;
                                if (h >= k) {
                                    h -= k;
                                }
                                best = Math.min(best, h + next[a - 1][b - 1]);
                                best = Math.min(best, ((reduced + 1) / 2) + next[a - 1][b - 1]);
                            }
                        }
                    }

                    curA[b] = best;
                }
            }

            next = cur;
        }

        return next[op1][op2];
    }       
}
