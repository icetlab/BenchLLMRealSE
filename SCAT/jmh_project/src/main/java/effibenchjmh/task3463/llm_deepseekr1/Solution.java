package effibenchjmh.task3463.llm_deepseekr1;

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
            if (colors[i] == colors[(i + 2) % n] && colors[i] != colors[(i + 1) % n]) {
                count++;
            }
        }
        return count;
    }
}
