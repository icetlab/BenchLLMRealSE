package effibenchjmh.task3334.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

import java.util.Arrays;

class Solution {
    public int minimumBoxes(int[] a, int[] c) {
        int ans = 0;
        int s = 0;
        for (int val : a) {
            s += val;
        }
        Arrays.sort(c);

        int i = c.length - 1;
        while (i >= 0) {
            s -= c[i];
            ans++;
            if (s <= 0)
                break;
            i--;
        }

        return ans;
    }
}
