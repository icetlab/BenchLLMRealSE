package effibenchjmh.task3739.llm_o4mini;

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
        long time = cnr(n * m - 2, k - 2);
        // sum of vertical (row) distances over all unordered row pairs
        long sumRow = 0;
        for (int d = 1; d < n; d++) {
            sumRow = (sumRow + (long) d * (n - d)) % M;
        }
        // sum of horizontal (column) distances over all unordered column pairs
        long sumCol = 0;
        for (int d = 1; d < m; d++) {
            sumCol = (sumCol + (long) d * (m - d)) % M;
        }
        // total distance = vertical_contrib * m^2 + horizontal_contrib * n^2
        long total = (sumRow * m % M * m % M + sumCol * n % M * n % M) % M;
        // multiply by C(totalCells-2, k-2)
        return (int) (total * time % M);
    }
}
