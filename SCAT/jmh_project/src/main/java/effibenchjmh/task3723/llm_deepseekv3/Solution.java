package effibenchjmh.task3723.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int sumOfGoodNumbers(int[] nums, int k) {
        int n = nums.length, sum = 0;
        for (int i = 0; i < n; i++) {
            if (i - k >= 0 && i + k < n) {
                if (nums[i + k] < nums[i] && nums[i - k] < nums[i])
                    sum += nums[i];
            } else if (i - k >= 0) {
                if (nums[i - k] < nums[i])
                    sum += nums[i];
            } else if (nums[i + k] < nums[i]) {
                sum += nums[i];
            }
        }
        return sum;
    }
}
