package effibenchjmh.task3334.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int minimumBoxes(int[] a, int[] c) {
        int ans = 0;
        int s = Arrays.stream(a).sum();
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
