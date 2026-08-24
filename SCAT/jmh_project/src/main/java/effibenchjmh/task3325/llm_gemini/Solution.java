package effibenchjmh.task3325.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public long largestSquareArea(int[][] bottomLeft, int[][] topRight) {
        long maxSide = 0;
        int n = bottomLeft.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                long interX1 = Math.max(bottomLeft[i][0], bottomLeft[j][0]);
                long interY1 = Math.max(bottomLeft[i][1], bottomLeft[j][1]);
                long interX2 = Math.min(topRight[i][0], topRight[j][0]);
                long interY2 = Math.min(topRight[i][1], topRight[j][1]);

                if (interX2 > interX1 && interY2 > interY1) {
                    long side = Math.min(interX2 - interX1, interY2 - interY1);
                    maxSide = Math.max(maxSide, side);
                }
            }
        }
        return maxSide * maxSide;
    }
}
