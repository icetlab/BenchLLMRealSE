package effibenchjmh.task3375.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    int n;
    long[] subsetLcms;
    int[] subsetParity;
    long gcd(long a, long b) {
        if (b==0) return a;
        return gcd(b,a%b);
    }
    boolean check(int[] coins, long k, long v) {
        long total = 0;
        int maxMask = 1 << n;
        for (int mask = 1; mask < maxMask; mask++) {
            long lcm = subsetLcms[mask];
            if (lcm == 0 || lcm > v) continue;
            long cnt = v / lcm;
            if (subsetParity[mask] == 1) total += cnt;
            else total -= cnt;
        }
        return total >= k;
    }
    public long findKthSmallest(int[] coins, int k) {
        n = coins.length;
        long minCoin = coins[0];
        for (int i = 1; i < n; i++) if (coins[i] < minCoin) minCoin = coins[i];
        subsetLcms = new long[1<<n];
        subsetParity = new int[1<<n];
        subsetLcms[0] = 1;
        subsetParity[0] = 0;
        for (int mask = 1; mask < (1<<n); mask++) {
            int lb = mask & -mask;
            int bit = Integer.numberOfTrailingZeros(lb);
            int prev = mask ^ lb;
            long prevLcm = subsetLcms[prev];
            subsetLcms[mask] = prevLcm * coins[bit] / gcd(prevLcm, coins[bit]);
            subsetParity[mask] = subsetParity[prev] ^ 1;
        }
        long l = 1;
        long h = minCoin * (long)k;
        while(l<=h) {
            long mid = l+(h-l)/2;
            if(check(coins,k,mid))
            h=mid-1;
            else
            l=mid+1;
        }
        return l;
    }
}
