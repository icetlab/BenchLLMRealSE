package effibenchjmh.task3375.llm_gemini;

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
    boolean check(int[] coins, long k, long v) {
        long total = 0;
        java.util.List<long[]> subsets = new java.util.ArrayList<>();
        subsets.add(new long[]{1, 0}); // {lcm, size}

        for (int coin : coins) {
            int currentSize = subsets.size();
            for (int i = 0; i < currentSize; i++) {
                long[] s = subsets.get(i);
                long currentLcm = s[0];
                
                long g = gcd(currentLcm, coin);
                // Check for overflow and if new lcm will exceed v. This prunes the search.
                if (currentLcm / g > v / coin) {
                    continue;
                }
                long nextLcm = currentLcm / g * coin;
                
                subsets.add(new long[]{nextLcm, s[1] + 1});
            }
        }
        
        // Calculate total using inclusion-exclusion principle, skipping the empty set
        for (int i = 1; i < subsets.size(); i++) {
            long lcm = subsets.get(i)[0];
            long count = subsets.get(i)[1];
            
            if (count % 2 == 1) {
                total += v / lcm;
            } else {
                total -= v / lcm;
            }
        }
        
        return total >= k;
    }
    public long findKthSmallest(int[] coins, int k) {
        long l=1;long h=Long.MAX_VALUE;
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
