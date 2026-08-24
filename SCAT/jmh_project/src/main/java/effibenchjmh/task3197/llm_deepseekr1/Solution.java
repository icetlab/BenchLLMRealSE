package effibenchjmh.task3197.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int maximumStrongPairXor(int[] A) {
        int res = 0;
        for (int i = 20; i >= 0; i--) {
            res <<= 1;
            HashMap<Integer, int[]> map = new HashMap<>();
            for (int a : A) {
                int p = a >> i;
                map.compute(p, (k, v) -> {
                    if (v == null) return new int[]{a, a};
                    v[0] = Math.min(v[0], a);
                    v[1] = Math.max(v[1], a);
                    return v;
                });
            }
            for (Map.Entry<Integer, int[]> entry : map.entrySet()) {
                int x = entry.getKey();
                int[] minmax = entry.getValue();
                int y = res ^ 1 ^ x;
                if (x >= y) {
                    int[] other = map.get(y);
                    if (other != null && minmax[0] <= 2 * other[1]) {
                        res |= 1;
                        break;
                    }
                }
            }
        }
        return res;
    }
}
