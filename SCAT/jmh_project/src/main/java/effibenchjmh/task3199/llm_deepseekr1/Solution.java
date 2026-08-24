package effibenchjmh.task3199.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int distributeCandies(int n, int limit) {
        int count=0;
        int maxI = Math.min(limit, n);
        for (int i = 0; i <= maxI; i++) {
            int remaining = n - i;
            int minJ = Math.max(0, remaining - limit);
            int maxJ = Math.min(limit, remaining);
            if (maxJ >= minJ) {
                count += (maxJ - minJ + 1);
            }
        }
        return count;
    }
}
