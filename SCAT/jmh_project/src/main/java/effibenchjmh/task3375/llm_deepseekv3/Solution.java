package effibenchjmh.task3375.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    long gcd(long a, long b) {
        if (b==0) return a;
        return gcd(b,a%b);
    }
    boolean check(int[] coins, long k, long v, long[] lcms, int[] counts) {
        int n=coins.length;
        long total =0;
        for(int i=1;i<(1<<n);i++) {
            if(lcms[i]==0) continue;
            if(counts[i]%2==0) total-=v/lcms[i];
            else total+=v/lcms[i];
        }
        return (total>=k);
    }
    public long findKthSmallest(int[] coins, int k) {
        int n=coins.length;
        long[] lcms = new long[1<<n];
        int[] counts = new int[1<<n];
        for(int i=1;i<(1<<n);i++) {
            int lsb = i & -i;
            int j = Integer.numberOfTrailingZeros(lsb);
            int prev = i ^ lsb;
            if(prev==0) {
                lcms[i] = coins[j];
                counts[i] = 1;
            } else {
                if(lcms[prev]==0) {
                    lcms[i]=0;
                } else {
                    long g = gcd(lcms[prev], (long)coins[j]);
                    if(lcms[prev] > Long.MAX_VALUE / coins[j]) {
                        lcms[i]=0;
                    } else {
                        lcms[i] = lcms[prev] / g * coins[j];
                    }
                }
                counts[i] = counts[prev] + 1;
            }
        }
        long minCoin = Long.MAX_VALUE;
        for(int c : coins) minCoin = Math.min(minCoin, c);
        long l=1;long h=minCoin*k;
        while(l<=h) {
            long mid = l+(h-l)/2;
            if(check(coins,k,mid,lcms,counts))
            h=mid-1;
            else
            l=mid+1;
        }
        return l;
    }
}
