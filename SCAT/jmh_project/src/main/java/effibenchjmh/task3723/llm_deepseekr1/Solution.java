package effibenchjmh.task3723.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int sumOfGoodNumbers(int[] nums, int k) {
        int n = nums.length;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            int v = nums[i];
            int left = i - k;
            int right = i + k;
            if ((right >= n || nums[right] < v) && (left < 0 || nums[left] < v)) {
                sum += v;
            }
        }
        return sum;
    }
}
