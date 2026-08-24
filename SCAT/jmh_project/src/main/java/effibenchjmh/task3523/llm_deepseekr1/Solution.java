package effibenchjmh.task3523.llm_deepseekr1;

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
        int cnt = 1;
        for (int i = 0; i < n; i++) {
            if (i > 0 && nums[i] > nums[i - 1] && 1 + nums[i - 1] == nums[i]) {
                cnt++;
            } else {
                cnt = 1;
            }
            if (cnt >= k) {
                ans[i - k + 1] = nums[i];
            } else if (i >= k - 1) {
                ans[i - k + 1] = -1;
            }
        }
        return ans;
    }
}
