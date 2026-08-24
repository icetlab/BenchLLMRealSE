package effibenchjmh.task3649.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int min =Integer.MAX_VALUE;
    public int breakLocks(List<Integer> list , int k){
        int time =0;
        int  x =1;
        for(int i =0;i<list.size();i++){
            time = time +(int)Math.ceil((double)list.get(i) / x);
            x = x+k;
        }
        return time ;
    }
    public void premutations(List<Integer> list, List<Integer> strength, boolean visited[],int k){
        if(list.size()==strength.size()){
            int time = breakLocks(list,k);
            min = Math.min(min,time);
            return;
        }
        for(int i =0;i<strength.size();i++){
            if(!visited[i]){
            visited[i]=true;
            list.add(strength.get(i));
            premutations(list,strength,visited,k);
            list.remove(list.size()-1);
            visited[i]=false;
            }
        }

    }
    public int findMinimumTime(List<Integer> strength, int k) {
        int n = strength.size();
        int[] strengths = new int[n];
        for (int i = 0; i < n; i++) strengths[i] = strength.get(i);

        long[][] cost = new long[n][n];
        for (int pos = 0; pos < n; pos++) {
            long x = 1L + (long) k * pos;
            for (int j = 0; j < n; j++) {
                int s = strengths[j];
                cost[pos][j] = (s + x - 1) / x;
            }
        }

        int totalMasks = 1 << n;
        long[] dp = new long[totalMasks];
        java.util.Arrays.fill(dp, Long.MAX_VALUE);
        dp[0] = 0;

        for (int mask = 0; mask < totalMasks; mask++) {
            if (dp[mask] == Long.MAX_VALUE) continue;
            int pos = Integer.bitCount(mask);
            for (int j = 0; j < n; j++) {
                if ((mask & (1 << j)) != 0) continue;
                int nextMask = mask | (1 << j);
                long nextTime = dp[mask] + cost[pos][j];
                if (nextTime < dp[nextMask]) dp[nextMask] = nextTime;
            }
        }

        return (int) dp[totalMasks - 1];
        
    }
}
