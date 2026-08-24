package effibenchjmh.task3355.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int minimumLevels(int[] possible) {
        int totalScore = 0;
        for (int p : possible) {
            totalScore += (p == 1 ? 1 : -1);
        }

        int n = possible.length;
        int aliceScore = 0;
        for (int i = 0; i < n - 1; i++) {
            aliceScore += (possible[i] == 1 ? 1 : -1);
            if (2 * aliceScore > totalScore) {
                return i + 1;
            }
        }

        return -1;
    }
}
