package effibenchjmh.task3739.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    static int M = (int) 1e9 + 7;
    static long[] fact = new long[(int) 1e5];

    static {
        fact[0] = 1;
        for (int i = 1; i < (int) 1e5; i++) {
            fact[i] = i * fact[i - 1];
            fact[i] %= M;
        }
    }

    public long cnr(int n, int r) {
        return fact[n] * power2(fact[n - r], M - 2) % M * power2(fact[r], M - 2) % M;
    }

    public static long power2(long x, long n) {

        long res = 1;
        while (n > 0) {
            if (n % 2 == 1) {
                res *= x;
                res %= M;
            }
            x *= x;
            x %= M;
            n >>= 1;
        }
        return res;
    }

        public int distanceSum(int m, int n, int k) {
        if ((long) m * n < k || k < 2) {
            return 0;
        }

        long time = cnr(m * n - 2, k - 2);

        long n_ = n;
        long m_ = m;

        // The sum of Manhattan distances between all unordered pairs of points is m*n*(m+n)*(m*n-1)/6
        long term_mn = (m_ * n_) % M;
        long term_m_plus_n = (m_ + n_) % M;
        long term_mn_minus_1 = (term_mn - 1 + M) % M;

        long totalDist_num = (term_mn * term_m_plus_n) % M;
        totalDist_num = (totalDist_num * term_mn_minus_1) % M;

        long inv6 = power2(6, M - 2);
        long totalDist = (totalDist_num * inv6) % M;

        long res = (totalDist * time) % M;
        return (int) res;
    }
}
