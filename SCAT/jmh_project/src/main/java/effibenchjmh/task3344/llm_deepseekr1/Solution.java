package effibenchjmh.task3344.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {

    class Point {
        int i;
        int val;
        Point(int i, int val) {
            this.i = i;
            this.val = val;
        }
    }

    public int minimumDistance(int[][] points) {
        int min1A = Integer.MAX_VALUE, min2A = Integer.MAX_VALUE;
        int max1A = Integer.MIN_VALUE, max2A = Integer.MIN_VALUE;
        int cntMinA = 0, cntMaxA = 0;
        int min1B = Integer.MAX_VALUE, min2B = Integer.MAX_VALUE;
        int max1B = Integer.MIN_VALUE, max2B = Integer.MIN_VALUE;
        int cntMinB = 0, cntMaxB = 0;

        for (int[] p : points) {
            int a = p[0] + p[1];
            int b = p[0] - p[1];

            if (a < min1A) {
                min2A = min1A;
                min1A = a;
                cntMinA = 1;
            } else if (a == min1A) {
                cntMinA++;
            } else if (a < min2A) {
                min2A = a;
            }

            if (a > max1A) {
                max2A = max1A;
                max1A = a;
                cntMaxA = 1;
            } else if (a == max1A) {
                cntMaxA++;
            } else if (a > max2A) {
                max2A = a;
            }

            if (b < min1B) {
                min2B = min1B;
                min1B = b;
                cntMinB = 1;
            } else if (b == min1B) {
                cntMinB++;
            } else if (b < min2B) {
                min2B = b;
            }

            if (b > max1B) {
                max2B = max1B;
                max1B = b;
                cntMaxB = 1;
            } else if (b == max1B) {
                cntMaxB++;
            } else if (b > max2B) {
                max2B = b;
            }
        }

        int res = Integer.MAX_VALUE;
        for (int[] p : points) {
            int a = p[0] + p[1];
            int b = p[0] - p[1];

            int minA = (a == min1A && cntMinA == 1) ? min2A : min1A;
            int maxA = (a == max1A && cntMaxA == 1) ? max2A : max1A;
            int minB = (b == min1B && cntMinB == 1) ? min2B : min1B;
            int maxB = (b == max1B && cntMaxB == 1) ? max2B : max1B;

            int cur = Math.max(maxA - minA, maxB - minB);
            if (cur < res) {
                res = cur;
            }
        }
        
        return res;
    }
}
