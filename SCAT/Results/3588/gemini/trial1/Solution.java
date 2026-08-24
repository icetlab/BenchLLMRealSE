public class Solution {
    private static final int MOD = 1_000_000_007;
    private String s;
    private int n;
    private static final int[][] RULES = {
        {0, 1, -1},  // E vs (E, F, W)
        {-1, 0, 1}, // F vs (E, F, W)
        {1, -1, 0}  // W vs (E, F, W)
    };
    private Integer[][][] memo;

    public int countWinningSequences(String s) {
        this.s = s;
        this.n = s.length();
        this.memo = new Integer[n + 1][4][2 * n + 1];
        return dp(0, 3, 0);
    }

    private int dp(int i, int last, int score) {
        if (score + (n - i) <= 0) return 0;
        if (i == n) return 1;
        if (memo[i][last][score + n] != null) return memo[i][last][score + n];

        int result = 0;
        char current = s.charAt(i);
        int currentIdx = (current == 'E') ? 0 : (current == 'F' ? 1 : 2);

        for (int j = 0; j < 3; j++) {
            if (j != last) {
                int gain = RULES[currentIdx][j];
                result = (result + dp(i + 1, j, score + gain)) % MOD;
            }
        }

        return memo[i][last][score + n] = result;
    }
}