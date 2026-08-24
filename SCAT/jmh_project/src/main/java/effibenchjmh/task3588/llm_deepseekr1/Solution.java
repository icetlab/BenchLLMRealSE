package effibenchjmh.task3588.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

public class Solution {
    private static final int MOD = 1_000_000_007;
    private static final int[][] GAIN = {
        {-1, 0, 1}, // current F
        {0, 1, -1}, // current E
        {1, -1, 0}  // current W
    };

    public int countWinningSequences(String s) {
        int n = s.length();
        if (n == 0) return 0;

        int[][] next = new int[3][2 * n + 1];
        for (int last = 0; last < 3; last++) {
            for (int score = -n; score <= n; score++) {
                next[last][score + n] = score > 0 ? 1 : 0;
            }
        }

        int[][] curDp = new int[3][2 * n + 1];

        for (int i = n - 1; i >= 1; i--) {
            int cur = charIndex(s.charAt(i));
            int remain = n - i;

            for (int last = 0; last < 3; last++) {
                for (int score = -i; score <= i; score++) {
                    int base = score + n;
                    if (score + remain <= 0) {
                        curDp[last][base] = 0;
                        continue;
                    }
                    int total = 0;
                    for (int j = 0; j < 3; j++) {
                        if (j != last) {
                            total += next[j][score + GAIN[cur][j] + n];
                            if (total >= MOD) total -= MOD;
                        }
                    }
                    curDp[last][base] = total;
                }
            }

            int[][] tmp = next;
            next = curDp;
            curDp = tmp;
        }

        int first = charIndex(s.charAt(0));
        int ans = 0;
        for (int j = 0; j < 3; j++) {
            ans += next[j][GAIN[first][j] + n];
            if (ans >= MOD) ans -= MOD;
        }
        return ans;
    }

    private int charIndex(char c) {
        if (c == 'F') return 0;
        if (c == 'E') return 1;
        return 2; // 'W'
    }
}
