package effibenchjmh.task3737.original;

import java.util.*;
import java.util.stream.*;

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

        //as 0,1,2 hi colors hai and first , aur uska pair last isko to kisi bhi color se kr skte hai, to inke liye prev color 3 dia hai

        dp = new Long[n+1][4][4];
        return func(0,3,3,cost);
        
    }
}
