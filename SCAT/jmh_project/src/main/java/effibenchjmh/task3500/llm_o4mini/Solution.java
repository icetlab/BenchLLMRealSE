package effibenchjmh.task3500.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public long minimumCost(int m, int n, int[] horizontalCut, int[] verticalCut) {
         Arrays.sort(horizontalCut);
        Arrays.sort(verticalCut);

        int hi = horizontalCut.length - 1, vi = verticalCut.length - 1;
        long cost = 0;
        long hp = 1, vp = 1;

        while (hi >= 0 && vi >= 0) {
            if (horizontalCut[hi] > verticalCut[vi]) {
                cost += (long) horizontalCut[hi] * vp;
                hp++;
                hi--;
            } else {
                cost += (long) verticalCut[vi] * hp;
                vp++;
                vi--;
            }
        }

        while (hi >= 0) {
            cost += (long) horizontalCut[hi] * vp;
            hp++;
            hi--;
        }

        while (vi >= 0) {
            cost += (long) verticalCut[vi] * hp;
            vp++;
            vi--;
        }

        return cost;
    }
}
