package effibenchjmh.task3618.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    private final long mod = 1000000007L;

    public int possibleStringCount(String word, int k) {
        ArrayList<Integer> list = new ArrayList<>();
        if (!word.isEmpty()) {
            int count = 1;
            for (int i = 1; i < word.length(); i++) {
                if (word.charAt(i) == word.charAt(i - 1)) {
                    count++;
                } else {
                    list.add(count);
                    count = 1;
                }
            }
            list.add(count);
        }

        // case 1
        if (word.length() < k) return 0;

        // case 2
        long res = 1L;
        if (list.size() >= k) {
            for (int t: list) {
                res *= t;
                res %= mod;
            }
            return (int) res;
        }
        
        // case 3
        long[] preSum = new long[k + 1];
        Arrays.fill(preSum, 1L);
        long[] preSum_idp = new long[k + 1];

        for (int itemCnt : list) {
            long[] cnt = new long[k + 1];

            for (int currLen = k; currLen >= 1; currLen--) {
                long duration = preSum[currLen - 1] - (currLen - itemCnt <= 0 ? 0 : preSum[currLen - itemCnt - 1]);
                duration = (duration + mod) % mod;
                cnt[currLen] = (cnt[currLen] + duration) % mod;
            }

            int a = Math.max(0, k - itemCnt + 1);
            if (a <= k) {
                long sum_idp = (preSum_idp[k] - (a > 0 ? preSum_idp[a - 1] : 0) + mod) % mod;
                long sum_dp = (preSum[k] - (a > 0 ? preSum[a - 1] : 0) + mod) % mod;

                long term2 = 1L * (itemCnt - k) * sum_dp;
                long total_add = (sum_idp + (term2 % mod) + mod) % mod;

                cnt[k] = (cnt[k] + total_add) % mod;
            }

            long[] newPreSum = new long[k + 1];
            long[] newPreSum_idp = new long[k + 1];
            for (int i = 1; i <= k; i++) {
                newPreSum[i] = (newPreSum[i - 1] + cnt[i]) % mod;
                long term = (1L * i * cnt[i]) % mod;
                newPreSum_idp[i] = (newPreSum_idp[i - 1] + term) % mod;
            }
            preSum = newPreSum;
            preSum_idp = newPreSum_idp;
        }

        return (int) ((preSum[k] - preSum[k-1] + mod) % mod);
    }
}
