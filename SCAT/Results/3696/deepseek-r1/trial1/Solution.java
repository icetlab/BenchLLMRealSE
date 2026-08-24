class Solution {
    String s;
    int n;
    int[] digit;             
    long[][][] dp;           

    public long countSubstrings(String s) {
        int len = s.length();
        long total = 0;

        for (int last = 1; last <= 9; last++) {
            long[] next = new long[last + 1];
            long[] cur = new long[last + 1];

            for (int i = len - 1; i >= 0; i--) {
                int d = s.charAt(i) - '0';

                for (int prod = 0; prod < last; prod++) {
                    int p2 = (prod * 10 + d) % last;
                    cur[prod] = ((d == last && p2 == 0) ? 1L : 0L) + next[p2];
                }

                int p2 = d % last;
                cur[last] = ((d == last && p2 == 0) ? 1L : 0L) + next[p2];

                total += cur[last];

                long[] tmp = cur;
                cur = next;
                next = tmp;
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
