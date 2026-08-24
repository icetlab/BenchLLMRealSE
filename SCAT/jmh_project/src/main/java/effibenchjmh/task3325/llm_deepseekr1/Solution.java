package effibenchjmh.task3325.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public long largestSquareArea(int[][] bottomLeft, int[][] topRight) {
        int n = bottomLeft.length;
        long max = 0;
        for (int i = 0; i < n; i++) {
            int bx1 = bottomLeft[i][0], by1 = bottomLeft[i][1];
            int tx1 = topRight[i][0], ty1 = topRight[i][1];
            for (int j = i + 1; j < n; j++) {
                int bx2 = bottomLeft[j][0], by2 = bottomLeft[j][1];
                int tx2 = topRight[j][0], ty2 = topRight[j][1];
                int x1 = Math.max(bx1, bx2);
                int x2 = Math.min(tx1, tx2);
                int y1 = Math.max(by1, by2);
                int y2 = Math.min(ty1, ty2);
                int xDim = x2 - x1;
                int yDim = y2 - y1;
                if (xDim > 0 && yDim > 0) {
                    long size = Math.min(xDim, yDim);
                    if (size > max) max = size;
                }
            }
        }
        return max * max;
    }
}
