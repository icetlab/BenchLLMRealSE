package effibenchjmh.task3523.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int[] resultsArray(int[] nums, int k) {
        int n = nums.length;
        int[] ans = new int[n - k + 1];
        int run = 1;
        for (int i = 0; i < n; i++) {
            if (i > 0 && nums[i] == nums[i - 1] + 1) {
                run++;
            } else if (i > 0) {
                run = 1;
            }
            if (i >= k - 1) {
                ans[i - k + 1] = (run >= k) ? nums[i] : -1;
            }
        }
        return ans;
    }
}
