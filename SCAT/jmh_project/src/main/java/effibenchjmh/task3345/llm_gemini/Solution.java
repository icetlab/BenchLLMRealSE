package effibenchjmh.task3345.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int mod = (int) 1e9+7;
    private long power(long base, long exp) {
        long res = 1;
        base %= mod;
        while (exp > 0) {
            if (exp % 2 == 1) res = (res * base) % mod;
            base = (base * base) % mod;
            exp /= 2;
        }
        return res;
    }

    public int sumOfPower(int[] nums, int k) {
        int n = nums.length;
        long[] dp = new long[k + 1];
        dp[0] = 1;

        // Modular inverse of 2, since mod is prime, inv(2) = 2^(mod-2)
        long inv2 = power(2, mod - 2);

        for (int num : nums) {
            for (int j = k; j >= num; j--) {
                dp[j] = (dp[j] + dp[j - num] * inv2) % mod;
            }
        }

        long powerOf2_n = power(2, n);
        long result = (dp[k] * powerOf2_n) % mod;

        return (int) result;
    }
}
