package effibenchjmh.task3737;

import java.util.*;
import java.util.stream.*;

class Solution {
    public long minCost(int n, int[][] cost) {
        int m = n / 2;
        long[][] prevDp = new long[3][3];
        for (int a = 0; a < 3; a++) {
            for (int b = 0; b < 3; b++) {
                prevDp[a][b] = Long.MAX_VALUE;
            }
        }
        int firstLeft = 0;
        int firstRight = n - 1;
        for (int a = 0; a < 3; a++) {
            for (int b = 0; b < 3; b++) {
                if (a != b) {
                    prevDp[a][b] = (long) cost[firstLeft][a] + cost[firstRight][b];
                }
            }
        }
        for (int p = 1; p < m; p++) {
            int left = p;
            int right = n - 1 - p;
            long[][] currDp = new long[3][3];
            for (int a = 0; a < 3; a++) {
                for (int b = 0; b < 3; b++) {
                    currDp[a][b] = Long.MAX_VALUE;
                }
            }
            for (int aPrev = 0; aPrev < 3; aPrev++) {
                for (int bPrev = 0; bPrev < 3; bPrev++) {
                    if (prevDp[aPrev][bPrev] == Long.MAX_VALUE) continue;
                    for (int a = 0; a < 3; a++) {
                        if (a == aPrev) continue;
                        for (int b = 0; b < 3; b++) {
                            if (b == bPrev || b == a) continue;
                            long newCost = prevDp[aPrev][bPrev] + cost[left][a] + cost[right][b];
                            if (newCost < currDp[a][b]) {
                                currDp[a][b] = newCost;
                            }
                        }
                    }
                }
            }
            prevDp = currDp;
        }
        long min = Long.MAX_VALUE;
        for (int a = 0; a < 3; a++) {
            for (int b = 0; b < 3; b++) {
                if (prevDp[a][b] < min) {
                    min = prevDp[a][b];
                }
            }
        }
        return min;
    }
}
