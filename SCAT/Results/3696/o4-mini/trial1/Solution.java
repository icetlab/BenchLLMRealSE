class Solution {
    String s;
    int n;
    int[] digit;             
    long[][][] dp;           

    public long countSubstrings(String s) {
        int n = s.length();
        int[] digit = new int[n];
        for (int i = 0; i < n; i++) digit[i] = s.charAt(i) - '0';
        long total = 0;
        for (int last = 1; last <= 9; last++) {
            long[] dpNext = new long[last];
            for (int idx = n - 1; idx >= 0; idx--) {
                int d = digit[idx];
                long[] dpCur = new long[last];
                for (int prod = 0; prod < last; prod++) {
                    int newProd = (prod * 10 + d) % last;
                    dpCur[prod] = dpNext[newProd];
                    if (d == last && newProd == 0) dpCur[prod]++;
                }
                int initNewProd = d % last;
                long cnt = dpNext[initNewProd] + ((d == last && initNewProd == 0) ? 1 : 0);
                total += cnt;
                dpNext = dpCur;
            }
        }
        return total;
    }

    private long solve(int idx, int last, int prod) {
        if (idx == n) return 0;
        long memo = dp[idx][last][prod];
        if (memo != -1) return memo;
        int d = digit[idx];
        int newProd = (prod == 10)
                      ? d % last
                      : (int) ((prod * 10 + d) % last);

        long cnt = (d == last && newProd == 0) ? 1 : 0;
        cnt += solve(idx + 1, last, newProd);
        dp[idx][last][prod] = cnt;
        return cnt;
    }
}
