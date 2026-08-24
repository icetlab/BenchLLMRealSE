package effibenchjmh.task3595.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public boolean isPossibleToRearrange(String s, String t, int k) {
        if(s.length()%k!=0 || s.length()!=t.length()){
            return false;
        }

        int n = s.length();
        int m = n / k;
        long[] sHash = new long[k];
        long[] tHash = new long[k];
        final long base = 91138233L;
        for (int i = 0; i < k; i++) {
            long h1 = 0, h2 = 0;
            for (int j = 0; j < m; j++) {
                h1 = h1 * base + s.charAt(i * m + j);
                h2 = h2 * base + t.charAt(i * m + j);
            }
            sHash[i] = h1;
            tHash[i] = h2;
        }
        Arrays.sort(sHash);
        Arrays.sort(tHash);
        for (int i = 0; i < k; i++) {
            if (sHash[i] != tHash[i]) {
                return false;
            }
        }
        return true;
    }
}
