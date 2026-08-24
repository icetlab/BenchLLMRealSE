package effibenchjmh.task3446.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

import java.util.Hashtable;
class Solution {
    public int numberOfPairs(int[] nums1, int[] nums2, int k) {

        java.util.Map<Integer, Integer> nums1Freq = new java.util.HashMap<>();
        int max = 0;
        for (int x : nums1) {
            nums1Freq.merge(x, 1, Integer::sum);
            if (x > max) max = x;
        }

        java.util.Map<Integer, Integer> productFreq = new java.util.HashMap<>();
        for (int b : nums2) {
            long p = (long) b * k;
            if (p > 0 && p <= max) {
                productFreq.merge((int) p, 1, Integer::sum);
            }
        }

        int count = 0;
        for (java.util.Map.Entry<Integer, Integer> e : nums1Freq.entrySet()) {
            int x = e.getKey();
            int times = e.getValue();
            for (int d = 1; d <= x / d; d++) {
                if (x % d == 0) {
                    count += times * productFreq.getOrDefault(d, 0);
                    int other = x / d;
                    if (other != d) {
                        count += times * productFreq.getOrDefault(other, 0);
                    }
                }
            }
        }
        return count;
    }
}
