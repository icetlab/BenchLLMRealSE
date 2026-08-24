package effibenchjmh.task3737.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

/*

Completely diff approach from brute force : but easily soch skte thee,
ki paint hi dono pair ko sath mei kro, aur dono ke adjacent color ko dp state
mei rkho, taaki pta rhe ki dono mei konse konse color bhr skte hai

fir bhrte time bhi dhyan rkho ki dono mei same color nhi bhrna
*/
class Solution {

    Long[][][] dp ;

    long func(int i, int pc, int fc, int[][] cost){

        int n = cost.length;
        if(i>=n/2)
            return 0;

        if(dp[i][pc][fc]!=null)
            return dp[i][pc][fc];


        List<Integer> PrevColorChoice = new ArrayList<>();
        List<Integer> FwdColorChoice = new ArrayList<>();

        for(int j = 0;j<3;j++)
        {
            if(pc != j){
                PrevColorChoice.add(j);
            }

            if(fc != j){
                FwdColorChoice.add(j);
            }

        }

        long ans = Long.MAX_VALUE;

        for(int p : PrevColorChoice){
            for(int f : FwdColorChoice){
                if(p==f)
                    continue;

                long cp = cost[i][p];
                long cf = cost[n-i-1][f];

                ans = Math.min(ans, cp + cf + func(i+1, p, f, cost));
            }
        }

        return dp[i][pc][fc] = ans; 

    }

    public long minCost(int n, int[][] cost) {
    int half = n/2;
    long INF = Long.MAX_VALUE/4;
    // dpNext represents dp[i+1][prevColor][fwdColor]
    long[][] dpNext = new long[4][4];
    // base case: at i == half, cost is zero for all states
    for (int pc = 0; pc < 4; pc++) {
        for (int fc = 0; fc < 4; fc++) {
            dpNext[pc][fc] = 0;
        }
    }
    // build from i = half-1 down to 0
    for (int i = half - 1; i >= 0; i--) {
        long[][] dpCur = new long[4][4];
        for (int pc = 0; pc < 4; pc++) {
            for (int fc = 0; fc < 4; fc++) {
                long best = INF;
                // try all valid colors p,f for positions i and n-i-1
                for (int p = 0; p < 3; p++) {
                    if (p == pc) continue;
                    for (int f = 0; f < 3; f++) {
                        if (f == fc || f == p) continue;
                        long val = cost[i][p] + cost[n - i - 1][f] + dpNext[p][f];
                        if (val < best) best = val;
                    }
                }
                dpCur[pc][fc] = best;
            }
        }
        dpNext = dpCur;
    }
    return dpNext[3][3];
}
}
