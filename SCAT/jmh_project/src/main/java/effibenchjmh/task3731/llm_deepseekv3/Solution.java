package effibenchjmh.task3731.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int subarraySum(int[] nums) {

        int n = nums.length;
        int sum = 0;
        int[] prefix = new int[n];

        prefix[0] = nums[0];
        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] + nums[i];
        }

        // [2, 5, 1, 5]
        // [2, 7, 8, 13]

        // 2 + 7 = 9 + 6 = 15 + 11 = 26

        for (int i = 0; i < n; i++) {
            int start = Math.max(0, i - nums[i]);
            sum += prefix[i] - (start > 0 ? prefix[start - 1] : 0);
        }

        return sum;
        
    }
}
