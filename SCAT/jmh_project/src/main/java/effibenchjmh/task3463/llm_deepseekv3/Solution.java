package effibenchjmh.task3463.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int numberOfAlternatingGroups(int[] colors) {
        int n = colors.length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            int a = colors[i];
            int b = colors[(i + 1) % n];
            int c = colors[(i + 2) % n];
            if (a != b && b != c && a == c) {
                count++;
            }
        }
        return count;
    }
}
