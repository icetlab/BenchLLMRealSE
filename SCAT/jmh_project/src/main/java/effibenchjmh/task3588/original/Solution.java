package effibenchjmh.task3588.original;

import java.util.*;
import java.util.stream.*;

public class Solution {
    private static final int MOD = 1_000_000_007;
    private String s;
    private int n;
    private Map<String, Integer> rules;
    private Integer[][][] memo;

    public int countWinningSequences(String s) {
        this.s = s;
        this.n = s.length();
        this.memo = new Integer[n + 1][4][2 * n + 1];
        initRules();
        return dp(0, 3, 0);
    }

    private void initRules() {
        rules = new HashMap<>();
        rules.put("FE", -1);
        rules.put("FW", 1);
        rules.put("WF", -1);
        rules.put("WE", 1);
        rules.put("EF", 1);
        rules.put("EW", -1);
    }

    private int dp(int i, int last, int score) {
        if (score + (n - i) <= 0) return 0;
        if (i == n) return 1;
        if (memo[i][last][score + n] != null) return memo[i][last][score + n];

        int result = 0;
        char current = s.charAt(i);

        for (int j = 0; j < 3; j++) {
            if (j != last) {
                char summon = "EFW".charAt(j);
                int gain = rules.getOrDefault("" + current + summon, 0);
                result = (result + dp(i + 1, j, score + gain)) % MOD;
            }
        }

        return memo[i][last][score + n] = result;
    }
}
