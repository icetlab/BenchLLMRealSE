package effibenchjmh.task3278.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int numberOfPairs(int[][] points) {
        Arrays.sort(points, (p1, p2) -> p1[0] != p2[0] ? p1[0] - p2[0] : p2[1] - p1[1]);

        int n = points.length;
        int count = 0;

        for (int i = 0; i < n; i++) {
            int yi = points[i][1];
            int maxYIntermediate = Integer.MIN_VALUE;
            for (int j = i + 1; j < n; j++) {
                int yj = points[j][1];
                if (yj <= yi) {
                    if (yj > maxYIntermediate) {
                        count++;
                    }
                    maxYIntermediate = Math.max(maxYIntermediate, yj);
                }
            }
        }

        return count;
    }
}
