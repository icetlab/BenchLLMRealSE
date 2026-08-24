package effibenchjmh.task3570.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int countOfSubstrings(String word, int k) {
        int n = word.length();

        boolean[] vowel = new boolean[128];
        vowel['a'] = true;
        vowel['e'] = true;
        vowel['i'] = true;
        vowel['o'] = true;
        vowel['u'] = true;

        int[] pref = new int[n + 1];
        for (int i = 0; i < n; i++) {
            char c = word.charAt(i);
            pref[i + 1] = pref[i] + (vowel[c] ? 0 : 1);
        }

        int[] first = new int[n + 1];
        int[] last = new int[n + 1];
        for (int i = 0; i <= n; i++) first[i] = -1;
        for (int i = 0; i <= n; i++) {
            int v = pref[i];
            if (first[v] == -1) first[v] = i;
            last[v] = i;
        }

        int[] lastPos = new int[26];
        for (int i = 0; i < 26; i++) lastPos[i] = -1;
        int res = 0;

        for (int j = 0; j < n; j++) {
            char c = word.charAt(j);
            if (vowel[c]) lastPos[c - 'a'] = j;

            int minLast = Math.min(lastPos[0], Math.min(lastPos[4],
                    Math.min(lastPos[8], Math.min(lastPos[14], lastPos[20]))));
            if (minLast < 0) continue;

            int target = pref[j + 1] - k;
            if (target < 0 || target > n) continue;

            int lo = first[target];
            if (lo == -1 || lo > minLast) continue;
            int hi = last[target];
            res += (hi < minLast ? hi : minLast) - lo + 1;
        }

        return res;
    }
}
