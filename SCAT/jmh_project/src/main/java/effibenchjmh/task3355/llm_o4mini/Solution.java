package effibenchjmh.task3355.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int minimumLevels(int[] possible) {
        int n=possible.length;
        int totalSuffix = 0;
        for (int v : possible) {
            totalSuffix += (v == 1 ? 1 : -1);
        }
        int prefix = 0;
        for (int i = 0; i < n - 1; i++) {
            prefix += (possible[i] == 1 ? 1 : -1);
            totalSuffix -= (possible[i] == 1 ? 1 : -1);
            if (prefix > totalSuffix) {
                return i + 1;
            }
        }
        return -1;
    }
}
