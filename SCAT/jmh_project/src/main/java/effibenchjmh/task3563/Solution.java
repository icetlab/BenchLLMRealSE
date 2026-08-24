package effibenchjmh.task3563;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    int[][] dp = new int[2001][101];

    int solve(int i, int n, List<Integer> t, Map<Integer, List<Integer>> mp, int mask) {
        if (i == n) return 0;
        if (dp[mask][i] != -1) return dp[mask][i];

        int ans = 0;
        for (int j : mp.get(t.get(i))) {
            if ((mask & (1 << j)) == 0) {
                ans = Math.max(ans, t.get(i) + solve(i + 1, n, t, mp, (mask | (1 << j))));
            }
        }
        ans = Math.max(ans, solve(i + 1, n, t, mp, mask));
        return dp[mask][i] = ans;
    }

    public int maxScore(List<List<Integer>> v) {
        int n = v.size();
        int m = v.get(0).size();
        Set<Integer> s = new HashSet<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                s.add(v.get(i).get(j));
            }
        }

        List<Integer> t = new ArrayList<>(s);
        Collections.sort(t, Collections.reverseOrder());

        Map<Integer, List<Integer>> mp = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                mp.computeIfAbsent(v.get(i).get(j), k -> new ArrayList<>()).add(i);
            }
        }

        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        return solve(0, t.size(), t, mp, 0);
    }
}
