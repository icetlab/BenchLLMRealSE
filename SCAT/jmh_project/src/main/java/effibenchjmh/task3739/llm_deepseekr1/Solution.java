package effibenchjmh.task3739.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    static int M = (int) 1e9 + 7;
    static long[] fact = new long[(int) 1e5];
    static long[] invFact = new long[(int) 1e5];

    static {
        fact[0] = 1;
        for (int i = 1; i < (int) 1e5; i++) {
            fact[i] = i * fact[i - 1];
            fact[i] %= M;
        }
        invFact[(int) 1e5 - 1] = power2(fact[(int) 1e5 - 1], M - 2);
        for (int i = (int) 1e5 - 1; i >= 1; i--) {
            invFact[i - 1] = invFact[i] * i % M;
        }
    }

    public long cnr(int n, int r) {
        return fact[n] * invFact[n - r] % M * invFact[r] % M;
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
        long time = cnr(n * m - 2, k - 2);

        int maxX = n - 1;
        int maxY = m - 1;
        long[] pref1 = new long[maxX + 1];
        long[] pref2 = new long[maxX + 1];
        for (int x = 1; x <= maxX; x++) {
            pref1[x] = pref1[x - 1] + (n - x);
            pref2[x] = pref2[x - 1] + (long) x * (n - x);
        }

        long res = 0;
        for (int d = 1; d <= n + m - 2; d++) {
            long cnt = 0;
            if (d < m) {
                cnt += (long) n * (m - d);
            }
            if (d < n) {
                cnt += (long) m * (n - d);
            }

            int lo = Math.max(1, d - maxY);
            int hi = Math.min(d - 1, maxX);
            if (lo <= hi) {
                long sum1 = pref1[hi] - pref1[lo - 1];
                long sum2 = pref2[hi] - pref2[lo - 1];
                long both = (m - d) * sum1 + sum2;
                cnt += 2 * both;
            }

            res = (res + (long) d * (cnt % M) % M * time) % M;
        }

        return (int) res;
    }
}
