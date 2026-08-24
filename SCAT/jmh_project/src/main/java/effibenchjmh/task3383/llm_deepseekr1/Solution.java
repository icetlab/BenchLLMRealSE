package effibenchjmh.task3383.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int maximumEnergy(int[] energy, int k) {
        int n = energy.length;
        int[] tail = new int[Math.min(k, n)];
        int max = Integer.MIN_VALUE;

        for (int i = n - 1; i >= 0; i--) {
            int r = i % k;
            tail[r] += energy[i];
            if (tail[r] > max) {
                max = tail[r];
            }
        }

        return max;
    }
}
