package effibenchjmh.task3536.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int countOfPairs(int[] nums) {
        int n = nums.length;
        final int MOD = 1000000007;

        int max = 0;
        for (int v : nums) {
            max = Math.max(max, v);
        }

        int[] next = new int[max + 1];
        int last = nums[n - 1];
        for (int j = 0; j <= max; j++) {
            next[j] = (j <= last) ? j + 1 : last + 1;
        }

        int[] cur = new int[max + 1];
        for (int i = n - 2; i >= 0; i--) {
            int a = nums[i];
            int b = nums[i + 1];
            int delta = b - a;
            int base = next[b];
            int prefix = 0;

            for (int j = 0; j <= a; j++) {
                int l = (delta > 0) ? j + delta : j;
                int raw = base;
                if (l > 0) {
                    raw -= next[l - 1];
                }
                if (raw < 0) {
                    raw += MOD;
                }
                prefix += raw;
                if (prefix >= MOD) {
                    prefix -= MOD;
                }
                cur[j] = prefix;
            }

            for (int j = Math.max(0, a + 1); j <= max; j++) {
                cur[j] = prefix;
            }

            int[] tmp = cur;
            cur = next;
            next = tmp;
        }

        return next[max];
    }
}
