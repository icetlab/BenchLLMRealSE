package effibenchjmh.task3375.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    long gcd(long a, long b) {
        if (b==0) return a;
        return gcd(b,a%b);
    }
    boolean check(int[] coins, long k, long v) {
        int n=coins.length;
        long total =0;
        for(int i=1;i<(1<<n);i++) {
            long lcm=0;int count=0;
            for(int j=0;j<n;j++) {
                if((i&(1<<j))>0){
                    count++;
                    if(lcm==0) lcm=coins[j];
                    else {
                        lcm=lcm*coins[j]/gcd(lcm,(long)coins[j]);
                    }
                }
            }
            if(count%2==0) total-=v/lcm;
            else total+=v/lcm;
        }
        return (total>=k);
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
