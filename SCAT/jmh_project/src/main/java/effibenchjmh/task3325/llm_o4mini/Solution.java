package effibenchjmh.task3325.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {


    public long largestSquareArea(int[][] bottomLeft, int[][] topRight) {

        int n = bottomLeft.length;
        int[] x1 = new int[n], y1 = new int[n], x2 = new int[n], y2 = new int[n];
        for (int i = 0; i < n; i++) {
            x1[i] = bottomLeft[i][0];
            y1[i] = bottomLeft[i][1];
            x2[i] = topRight[i][0];
            y2[i] = topRight[i][1];
        }

        long max = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int ox1 = Math.max(x1[i], x1[j]);
                int ox2 = Math.min(x2[i], x2[j]);
                int oy1 = Math.max(y1[i], y1[j]);
                int oy2 = Math.min(y2[i], y2[j]);
                int xDim = ox2 - ox1, yDim = oy2 - oy1;
                long size = (xDim > 0 && yDim > 0) ? Math.min(xDim, yDim) : 0;
                if (size > max) max = size;
            }
        }

        return max * max;
    }


}
