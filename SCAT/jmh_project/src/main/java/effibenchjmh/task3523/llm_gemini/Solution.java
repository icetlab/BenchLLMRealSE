package effibenchjmh.task3523.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int[] resultsArray(int[] nums, int k) {
        if (nums.length < k) {
            return new int[0];
        }

        int[] ans = new int[nums.length - k + 1];
        int consecutiveLength = 0;

        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1] + 1) {
                consecutiveLength++;
            } else {
                consecutiveLength = 1;
            }

            if (i >= k - 1) {
                if (consecutiveLength >= k) {
                    ans[i - k + 1] = nums[i];
                } else {
                    ans[i - k + 1] = -1;
                }
            }
        }
        return ans;
    }
}
