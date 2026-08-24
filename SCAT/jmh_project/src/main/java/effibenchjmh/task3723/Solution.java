package effibenchjmh.task3723;

import java.util.*;
import java.util.stream.*;

class Solution {
    public int sumOfGoodNumbers(int[] nums, int k) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            boolean good = true;
            int left = i - k;
            if (left >= 0 && nums[i] <= nums[left]) {
                good = false;
            }
            int right = i + k;
            if (right < nums.length && nums[i] <= nums[right]) {
                good = false;
            }
            if (good) {
                sum += nums[i];
            }
        }
        return sum;
    }
}
