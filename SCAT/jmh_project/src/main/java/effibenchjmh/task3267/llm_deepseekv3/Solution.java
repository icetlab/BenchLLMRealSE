package effibenchjmh.task3267.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int maximumLength(String s) {
        int n = s.length();
        int[][] freq = new int[26][n + 1];
        int maxLen = -1;
        
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            int idx = c - 'a';
            int len = 1;
            while (i + len < n && s.charAt(i + len) == c) {
                len++;
            }
            for (int l = 1; l <= len; l++) {
                freq[idx][l] += len - l + 1;
                if (freq[idx][l] >= 3) {
                    maxLen = Math.max(maxLen, l);
                }
            }
            i += len - 1;
        }
        return maxLen;
    }
}
