package effibenchjmh.task3731;

import java.util.*;
import java.util.stream.*;

class Solution {
    public int subarraySum(int[] nums) {
        int total = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int start = Math.max(0, i - nums[i]);
            for (int j = start; j <= i; j++) {
                total += nums[j];
            }
        }
        return total;
    }
}
