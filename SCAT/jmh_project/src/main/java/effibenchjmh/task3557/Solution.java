package effibenchjmh.task3557;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int minValidStrings(String[] words, String target) {
        int n = target.length();
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        List<List<Integer>> matches = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            matches.add(new ArrayList<>());
        }
        char[] targetChars = target.toCharArray();
        for (String word : words) {
            char[] wordChars = word.toCharArray();
            int m = wordChars.length;
            int[] pi = new int[m];
            for (int i = 1, j = 0; i < m; i++) {
                while (j > 0 && wordChars[i] != wordChars[j]) {
                    j = pi[j - 1];
                }
                if (wordChars[i] == wordChars[j]) {
                    j++;
                }
                pi[i] = j;
            }

            for (int i = 0, j = 0; i < n; i++) {
                while (j > 0 && targetChars[i] != wordChars[j]) {
                    j = pi[j - 1];
                }
                if (targetChars[i] == wordChars[j]) {
                    j++;
                }
                if (j > 0) {
                    matches.get(i - j + 1).add(j);
                    if (j == m) {
                        j = pi[j - 1]; 
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if (dp[i] == Integer.MAX_VALUE) continue;
            for (int len : matches.get(i)) {
                if (i + len <= n) {
                    dp[i + len] = Math.min(dp[i + len], dp[i] + 1);
                }
            }
        }
        return dp[n] == Integer.MAX_VALUE ? -1 : dp[n];
    }
}
