package effibenchjmh.task3335;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    void fill(int[][] vis, int[][] v) {
        int n = v.length;
        int i = 0, j = 0;
        while (i <= n / 2) {
            vis[i][j] = 1;
            i++;
            j++;
        }
        i = 0;
        j = n - 1;
        while (i < n / 2) {
            vis[i][j] = 1;
            i++;
            j--;
        }
        i = n / 2 + 1;
        j = n / 2;
        while (i < n) {
            vis[i][j] = 1;
            i++;
        }
    }

    public int minimumOperationsToWriteY(int[][] v) {
        int n = v.length;
        int[][] vis = new int[n][n];
        fill(vis, v);
        int[] v1 = new int[3];
        int[] v2 = new int[3];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (vis[i][j] == 1) {
                    if (v[i][j] == 0)
                        v1[0]++;
                    else if (v[i][j] == 1)
                        v1[1]++;
                    else
                        v1[2]++;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (vis[i][j] == 0) {
                    if (v[i][j] == 0)
                        v2[0]++;
                    else if (v[i][j] == 1)
                        v2[1]++;
                    else
                        v2[2]++;
                }
            }
        }

        int ans = Integer.MAX_VALUE;
        for (int i = 0; i <= 2; i++) {
            int costY = v1[0] + v1[1] + v1[2] - v1[i];
            int costRem = Integer.MAX_VALUE;
            for (int j = 0; j <= 2; j++) {
                if (i == j)
                    continue;
                int t = v2[0] + v2[1] + v2[2] - v2[j];
                costRem = Math.min(costRem, t);
            }
            ans = Math.min(ans, costY + costRem);
        }
        return ans;
    }
}
