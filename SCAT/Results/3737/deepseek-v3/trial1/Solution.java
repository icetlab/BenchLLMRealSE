/*

Completely diff approach from brute force : but easily soch skte thee,
ki paint hi dono pair ko sath mei kro, aur dono ke adjacent color ko dp state
mei rkho, taaki pta rhe ki dono mei konse konse color bhr skte hai

fir bhrte time bhi dhyan rkho ki dono mei same color nhi bhrna
*/
class Solution {

    public long minCost(int n, int[][] cost) {
        int half = n / 2;
        // dp[pc][fc] = minimum cost for current layer given previous colors
        long[][] dp = new long[4][4];
        long[][] next = new long[4][4];

        // Initialize base case: after processing all pairs, cost is 0
        // We'll iterate from the last pair backwards
        for (int i = half - 1; i >= 0; i--) {
            for (int pc = 0; pc < 4; pc++) {
                for (int fc = 0; fc < 4; fc++) {
                    long best = Long.MAX_VALUE;
                    for (int p = 0; p < 3; p++) {
                        if (p == pc) continue;
                        for (int f = 0; f < 3; f++) {
                            if (f == fc || p == f) continue;
                            long cur = cost[i][p] + cost[n - i - 1][f] + dp[p][f];
                            if (cur < best) best = cur;
                        }
                    }
                    next[pc][fc] = best;
                }
            }
            // swap dp and next
            long[][] temp = dp;
            dp = next;
            next = temp;
        }

        // Start with pc=3, fc=3 meaning no previous color constraint
        return dp[3][3];
    }
}