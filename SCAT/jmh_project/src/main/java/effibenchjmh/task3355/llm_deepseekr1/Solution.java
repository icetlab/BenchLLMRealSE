package effibenchjmh.task3355.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int minimumLevels(int[] possible) {
        int total = 0;
        for (int p : possible) {
            total += p == 0 ? -1 : 1;
        }

        int alice = 0;
        for (int i = 0; i < possible.length - 1; i++) {
            alice += possible[i] == 0 ? -1 : 1;
            if (alice > total - alice) {
                return i + 1;
            }
        }

        return -1;
    }
}
