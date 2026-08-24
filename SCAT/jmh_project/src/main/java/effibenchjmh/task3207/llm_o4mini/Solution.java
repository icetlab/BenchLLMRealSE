package effibenchjmh.task3207.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int findMinimumOperations(String s1, String s2, String s3) {
        int minLen = Math.min(s1.length(), Math.min(s2.length(), s3.length()));
        int L = 0;
        for (; L < minLen; L++) {
            char c = s1.charAt(L);
            if (s2.charAt(L) != c || s3.charAt(L) != c) break;
        }
        if (L == 0) return -1;
        return (s1.length() - L) + (s2.length() - L) + (s3.length() - L);
    }
}
