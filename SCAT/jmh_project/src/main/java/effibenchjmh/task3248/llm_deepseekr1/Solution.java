package effibenchjmh.task3248.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public long incremovableSubarrayCount(int[] nums) {
        int n = nums.length;
        int start = n - 1;
        while (start > 0 && nums[start - 1] < nums[start]) {
            start--;
        }

        if (start == 0) {
            return (long) n * (n + 1) / 2;
        }

        int suffixLen = n - start;
        long ans = 1L + suffixLen;
        int last = -1;
        int pos = 0;

        for (int i = 0; i < start; i++) {
            if (nums[i] <= last) {
                break;
            }
            last = nums[i];

            while (pos < suffixLen && nums[start + pos] <= nums[i]) {
                pos++;
            }

            ans += suffixLen - pos + 1;
        }

        return ans;
    }
}
