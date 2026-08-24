package effibenchjmh.task3500.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public long minimumCost(int m, int n, int[] horizontalCut, int[] verticalCut) {
         Integer[] hCuts = Arrays.stream(horizontalCut).boxed().toArray(Integer[]::new);
        Integer[] vCuts = Arrays.stream(verticalCut).boxed().toArray(Integer[]::new);

        // Sort in descending order
        Arrays.sort(hCuts, Collections.reverseOrder());
        Arrays.sort(vCuts, Collections.reverseOrder());

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
