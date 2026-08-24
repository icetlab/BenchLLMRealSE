package effibenchjmh.task3500.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public long minimumCost(int m, int n, int[] horizontalCut, int[] verticalCut) {
         int[] hCuts = horizontalCut.clone();
        int[] vCuts = verticalCut.clone();
        java.util.Arrays.sort(hCuts);
        java.util.Arrays.sort(vCuts);
        // Reverse to get descending order
        for (int i = 0, j = hCuts.length - 1; i < j; i++, j--) {
            int tmp = hCuts[i];
            hCuts[i] = hCuts[j];
            hCuts[j] = tmp;
        }
        for (int i = 0, j = vCuts.length - 1; i < j; i++, j--) {
            int tmp = vCuts[i];
            vCuts[i] = vCuts[j];
            vCuts[j] = tmp;
        }

        int h = 0, v = 0;
        long cost = 0;
        long hp = 1, vp = 1;  

        while (h < hCuts.length && v < vCuts.length) {
            if (vCuts[v] > hCuts[h]) {
                cost += (long) vCuts[v] * hp;  
                vp++;
                v++;
            } else {
                cost += (long) hCuts[h] * vp;  
                hp++;
                h++;
            }
        }


        while (h < hCuts.length) {
            cost += (long) hCuts[h] * vp;
            hp++;
            h++;
        }


        while (v < vCuts.length) {
            cost += (long) vCuts[v] * hp;
            vp++;
            v++;
        }

        return cost;
    }
}
