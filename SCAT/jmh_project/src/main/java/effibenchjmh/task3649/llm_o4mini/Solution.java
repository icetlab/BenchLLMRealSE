package effibenchjmh.task3649.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

import java.util.*;

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
        int N = 1 << n;
        int[] dp = new int[N];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        // dp[mask] = minimum time to break locks in 'mask'
        for (int mask = 1; mask < N; mask++) {
            // number of locks already broken before this one = popcount(mask)-1
            int cnt = Integer.bitCount(mask) - 1;
            int x = 1 + k * cnt;
            int temp = mask;
            while (temp != 0) {
                int lsb = temp & -temp;
                int j = Integer.numberOfTrailingZeros(lsb);
                int prev = mask ^ lsb;
                int s = strength.get(j);
                int t = (s + x - 1) / x;
                if (dp[prev] != Integer.MAX_VALUE) {
                    dp[mask] = Math.min(dp[mask], dp[prev] + t);
                }
                temp &= temp - 1;
            }
        }
        return dp[N - 1];
    }
}
