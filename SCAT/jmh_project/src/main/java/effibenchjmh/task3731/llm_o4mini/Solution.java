package effibenchjmh.task3731.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int subarraySum(int[] nums) {

        int n = nums.length;
        long sum = 0;
        long[] prefix = new long[n];

        for (int i = 0; i < n; i++) {
            if (i == 0) {
                prefix[0] = nums[0];
            } else {
                prefix[i] = prefix[i - 1] + nums[i];
            }
            int start = Math.max(0, i - nums[i]);
            if (start == 0) {
                sum += prefix[i];
            } else {
                sum += prefix[i] - prefix[start - 1];
            }
        }

        return (int) sum;
        
    }
}
