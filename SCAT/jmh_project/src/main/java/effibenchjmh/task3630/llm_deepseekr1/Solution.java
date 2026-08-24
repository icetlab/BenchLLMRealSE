package effibenchjmh.task3630.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    long mod = (long)1e9 + 7;
    long[] count = new long[26];//vector A
    long[][] M = new long[26][26];
    long[][] identityMatrix = new long[26][26];
    public int lengthAfterTransformations(String s, int t, List<Integer> nums) {
        for (int i = 0; i < 26; i++) {
            count[i] = 0;
        }
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                M[i][j] = 0;
            }
        }
        fillM(nums);
        for (char ch : s.toCharArray()) {
            count[ch - 'a']++;
        }
        for (int i = 0; i < 26; i++) {
            identityMatrix[i][i] = 1;
        }
        long[][] finalState = fastExp(M, t);

        long ans = 0;
        for (int from = 0; from < 26; from++) {
            if (count[from] == 0) {
                continue;
            }
            long rowSum = 0;
            for (int to = 0; to < 26; to++) {
                rowSum += finalState[from][to];
            }
            ans = (ans + (count[from] % mod) * (rowSum % mod)) % mod;
        }
        return (int) ans;

    }
    private long[][] fastExp(long[][] M, long t) {
        long[][] result = null;
        long[][] base = M;
        while (t > 0) {
            if ((t & 1) == 1) {
                result = (result == null) ? base : multiply(result, base);
            }
            t >>= 1;
            if (t > 0) {
                base = multiply(base, base);
            }
        }
        return result == null ? identityMatrix : result;
    }
    private long[][] multiply(long[][] A, long[][] B) {
        long[][] Bt = new long[26][26];
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                Bt[i][j] = B[j][i];
            }
        }
        long[][] res = new long[26][26];
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                long sum = 0;
                for (int k = 0; k < 26; k++) {
                    sum += A[i][k] * Bt[j][k];
                    if ((k & 7) == 7) {
                        sum %= mod;
                    }
                }
                res[i][j] = sum % mod;
            }
        }
        return res;
    }
    private void fillM(List<Integer> nums) {
        for (int i = 0; i < 26; i++) {
            int nextChars = nums.get(i); 
            int idx = (i + 1) % 26;
            for (int j = 0; j < nextChars; j++) {
                M[i][idx] = 1;
                idx = (idx + 1) % 26;
            }
        }
        
    }
}
