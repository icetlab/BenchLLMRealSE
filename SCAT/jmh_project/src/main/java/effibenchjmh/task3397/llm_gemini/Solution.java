package effibenchjmh.task3397.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int addedInteger(int[] nums1, int[] nums2) {
        long sum1 = 0;
        long sum2 = 0;
        for (int i = 0; i < nums1.length; i++) {
            sum1 += nums1[i];
            sum2 += nums2[i];
        }
        return (int) ((sum2 - sum1) / nums1.length);
    }
}

/**
class Solution {
    public int addedInteger(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);

        return nums2[0] - nums1[0];
    }
}
 */
