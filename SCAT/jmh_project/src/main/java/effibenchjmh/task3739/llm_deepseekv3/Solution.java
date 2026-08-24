package effibenchjmh.task3739.llm_deepseekv3;

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
            fact[i] = i * fact[i - 1] % M;
        }
        invFact[(int) 1e5 - 1] = power2(fact[(int) 1e5 - 1], M - 2);
        for (int i = (int) 1e5 - 1; i > 0; i--) {
            invFact[i - 1] = invFact[i] * i % M;
        }
    }

    public long cnr(int n, int r) {
        if (r < 0 || r > n) return 0;
        return fact[n] * invFact[n - r] % M * invFact[r] % M;
    }

    public static long power2(long x, long n) {
        long res = 1;
        x %= M;
        while (n > 0) {
            if ((n & 1) == 1) {
                res = res * x % M;
            }
            x = x * x % M;
            n >>= 1;
        }
        return res;
    }

    public int distanceSum(int m, int n, int k) {
        long total = (long) n * m;
        long time = cnr((int) total - 2, k - 2);
        if (time == 0) return 0;
        long sumDist = 0;
        // Sum over all unordered pairs of distinct cells of Manhattan distance.
        // For each pair of rows (a,b), contribution |a-b| * m^2.
        // For each pair of columns (c,d), contribution |c-d| * n^2.
        long rowSum = 0;
        for (int d = 1; d < n; d++) {
            rowSum = (rowSum + (long) d * (n - d)) % M;
        }
        long colSum = 0;
        for (int d = 1; d < m; d++) {
            colSum = (colSum + (long) d * (m - d)) % M;
        }
        sumDist = (rowSum * (m % M) % M * (m % M) + colSum * (n % M) % M * (n % M)) % M;
        long res = sumDist * time % M;
        return (int) res;
    }
}
