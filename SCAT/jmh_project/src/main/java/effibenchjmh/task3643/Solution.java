package effibenchjmh.task3643;

import java.util.*;
import java.util.stream.*;

class Solution {
    public int minZeroArray(int[] nums, int[][] queries) {
        int n = nums.length;
        int[] cnt = new int[n + 1];
        int s = 0, k = 0;

        for (int i = 0; i < n; ++i) {
            while (s + cnt[i] < nums[i]) {
                k++; // Increment k before accessing queries
                if (k - 1 >= queries.length) {
                    return -1;
                }
                int l = queries[k - 1][0], r = queries[k - 1][1], val = queries[k - 1][2];
                if (r < i) {
                    continue;
                }
                cnt[Math.max(l, i)] += val;
                cnt[r + 1] -= val;
            }
            s += cnt[i];
        }
        return k;
    }
}
