package effibenchjmh.task3463.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int numberOfAlternatingGroups(int[] colors) {
        int n = colors.length;
        if (n < 3) {
            return 0;
        }

        int count = 0;
        for (int i = 0; i < n; i++) {
            if (colors[i] != colors[(i + 1) % n] && colors[(i + 1) % n] != colors[(i + 2) % n]) {
                count++;
            }
        }

        return count;
    }
}
