package effibenchjmh.task3197.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

import java.util.HashMap;
import java.util.Map;

class Solution {
       public int maximumStrongPairXor(int[] A) {
        int res = 0;
        Map<Integer, int[]> map = new HashMap<>();
        for (int i = 20; i >= 0; i--) {
            res <<= 1;
            map.clear();
            for (int a : A) {
                int p = a >> i;
                int[] mm = map.get(p);
                if (mm == null) {
                    map.put(p, new int[] { a, a });
                } else {
                    if (a < mm[0]) mm[0] = a;
                    if (a > mm[1]) mm[1] = a;
                }
            }
            for (Map.Entry<Integer, int[]> e : map.entrySet()) {
                int x = e.getKey();
                int y = res ^ 1 ^ x;
                if (x >= y) {
                    int[] mm1 = e.getValue();
                    int[] mm2 = map.get(y);
                    if (mm2 != null && mm1[0] <= mm2[1] * 2) {
                        res |= 1;
                        break;
                    }
                }
            }
        }
        return res;
    }
}
