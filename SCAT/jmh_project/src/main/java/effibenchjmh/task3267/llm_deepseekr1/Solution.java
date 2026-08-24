package effibenchjmh.task3267.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int maximumLength(String s) {
        int n = s.length();
        int low = 0;
        int high = n + 1;

        while (low + 1 < high) {
            int len = low + (high - low) / 2;
            if (hasAtLeastThree(s, len)) {
                low = len;
            } else {
                high = len;
            }
        }

        return low == 0 ? -1 : low;
    }

    private boolean hasAtLeastThree(String s, int len) {
        int n = s.length();
        int[] counts = new int[65536];
        int i = 0;

        while (i < n) {
            char c = s.charAt(i);
            int j = i + 1;
            while (j < n && s.charAt(j) == c) {
                j++;
            }

            int runLen = j - i;
            if (runLen >= len) {
                int idx = c;
                counts[idx] += runLen - len + 1;
                if (counts[idx] >= 3) {
                    return true;
                }
            }

            i = j;
        }

        return false;
    }
}
