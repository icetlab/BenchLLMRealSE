package effibenchjmh.task3355.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int minimumLevels(int[] possible) {
        int n = possible.length;
        int total = 0;
        for (int p : possible) {
            total += (p == 1) ? 1 : -1;
        }
        int alice = 0;
        for (int i = 0; i < n - 1; i++) {
            alice += (possible[i] == 1) ? 1 : -1;
            int bob = total - alice;
            if (alice > bob) {
                return i + 1;
            }
        }
        return -1;
    }
}
