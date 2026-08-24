package effibenchjmh.task3334.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int minimumBoxes(int[] a, int[] c) {
        int ans = 0;
        int s = 0;
        for (int value : a) {
            s += value;
        }
        java.util.Arrays.sort(c);

        for (int i = c.length - 1; i >= 0; i--) {
            s -= c[i];
            ans++;
            if (s <= 0) {
                break;
            }
        }

        return ans;
    }
}
