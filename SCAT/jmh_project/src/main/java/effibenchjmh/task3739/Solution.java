package effibenchjmh.task3739;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    static final int MOD = 1000000007;
    static final int MAX = 100000;
    static long[] fact = new long[MAX + 1];
    static long[] invFact = new long[MAX + 1];
    static final int inv3 = 333333336;
    static final int inv2 = 500000004;

    static {
        fact[0] = 1;
        for (int i = 1; i <= MAX; i++) {
            fact[i] = fact[i - 1] * i % MOD;
        }
        invFact[MAX] = pow(fact[MAX], MOD - 2);
        for (int i = MAX - 1; i >= 0; i--) {
            invFact[i] = invFact[i + 1] * (i + 1) % MOD;
        }
    }

    private static long pow(long a, int b) {
        long res = 1;
        while (b > 0) {
            if ((b & 1) == 1) {
                res = res * a % MOD;
            }
            a = a * a % MOD;
            b >>= 1;
        }
        return res;
    }

    private static long comb(int a, int b) {
        if (a < 0 || b < 0 || a < b) {
            return 0;
        }
        return fact[a] * invFact[b] % MOD * invFact[a - b] % MOD;
    }

    public int distanceSum(int m, int n, int k) {
        int mn = m * n;
        if (k < 2 || k > mn) {
            return 0;
        }

        long mCube = (long) m * m % MOD * m % MOD;
        long mPart = (mCube - m + MOD) % MOD;
        long mTerm = (long) n * n % MOD;
        mTerm = mTerm * mPart % MOD;

        long nCube = (long) n * n % MOD * n % MOD;
        long nPart = (nCube - n + MOD) % MOD;
        long nTerm = (long) m * m % MOD;
        nTerm = nTerm * nPart % MOD;

        long sumManhattan = (mTerm + nTerm) % MOD;
        sumManhattan = sumManhattan * inv3 % MOD;

        long sumUnordered = sumManhattan * inv2 % MOD;

        long c = comb(mn - 2, k - 2);

        long ans = sumUnordered * c % MOD;

        return (int) ans;
    }
}
