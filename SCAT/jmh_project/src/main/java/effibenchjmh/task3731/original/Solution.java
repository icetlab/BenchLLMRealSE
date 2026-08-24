package effibenchjmh.task3731.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    public int subarraySum(int[] nums) {

        int n = nums.length;
        int sum = 0;
        int[] prefix = new int[n];
        int start = 0;

        prefix[0] = nums[0];
        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] + nums[i];
        }
        System.out.println();

        // [2, 5, 1, 5]
        // [2, 7, 8, 13]

        // 2 + 7 = 9 + 6 = 15 + 11 = 26

        for (int i = 0; i < n; i++) {
            start = Math.max(0, i - nums[i]);

            if (start == 0) {
                sum += prefix[i];
            } else {
                sum += prefix[i] - prefix[start - 1];
            }

        }

        return sum;
        
    }
}
