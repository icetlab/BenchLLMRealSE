package effibenchjmh.task3252.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
        public static  boolean check(List<Integer> al){
                for(int p=0;p<al.size()-1;p++){
                    if(al.get(p)>=al.get(p+1)){
                        return false;
                    }
                }
                return true;
        }
        public int incremovableSubarrayCount(int[] a) {
        int n = a.length;
        // pre[i]=true iff a[0..i-1] is strictly increasing
        boolean[] pre = new boolean[n+1];
        pre[0] = true;
        for (int i = 1; i <= n; i++) {
            pre[i] = (i >= 2 ? (pre[i-1] && a[i-1] > a[i-2]) : true);
        }
        // suf[k]=true iff a[k..n-1] is strictly increasing
        boolean[] suf = new boolean[n+1];
        suf[n] = true;
        for (int i = n - 1; i >= 0; i--) {
            suf[i] = (i <= n - 2 ? (suf[i+1] && a[i] < a[i+1]) : true);
        }
        // count of removals that leave only a prefix
        int pSize = 0;
        for (int i = 0; i < n; i++) {
            if (pre[i]) pSize++;
        }
        // collect all t where prefix of length t+1 is good
        java.util.List<Integer> ut = new java.util.ArrayList<>();
        for (int t = 0; t <= n - 2; t++) {
            if (pre[t+1]) ut.add(t);
        }
        // collect all k where suffix from k is good
        java.util.List<Integer> ks = new java.util.ArrayList<>();
        for (int k = 1; k <= n - 1; k++) {
            if (suf[k]) ks.add(k);
        }
        // coordinate compression of values
        int[] vals = java.util.Arrays.copyOf(a, n);
        java.util.Arrays.sort(vals);
        int m = 0;
        for (int i = 0; i < n; i++) {
            if (i == 0 || vals[i] != vals[i-1]) vals[m++] = vals[i];
        }
        // Fenwick tree over [1..m]
        int[] bit = new int[m+2];
        long ans = 0;
        int idxU = 0;
        // for each valid suffix start k, count how many prefixes t<a[k]
        for (int k : ks) {
            while (idxU < ut.size() && ut.get(idxU) <= k - 2) {
                int v = a[ut.get(idxU)];
                int pos = java.util.Arrays.binarySearch(vals, 0, m, v);
                int r = pos + 1;
                for (int x = r; x < bit.length; x += x & -x) bit[x]++;
                idxU++;
            }
            int v2 = a[k];
            int pos2 = java.util.Arrays.binarySearch(vals, 0, m, v2);
            int cnt = 0;
            for (int x = pos2; x > 0; x -= x & -x) cnt += bit[x];
            ans += 1 + cnt;
        }
        ans += pSize;
        return (int) ans;
    }
}
