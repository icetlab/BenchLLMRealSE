package effibenchjmh.task3731.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int subarraySum(int[] nums) {

        int n = nums.length;
        int[] prefix = new int[n];
        int sum = 0;
        int runningPrefix = 0;
        int[] deferred = null;
        int deferredCount = 0;

        for (int i = 0; i < n; i++) {
            int value = nums[i];
            runningPrefix += value;
            prefix[i] = runningPrefix;

            int start = i - value;
            if (start < 0) {
                sum += runningPrefix;
            } else if (start > i) {
                sum += runningPrefix;
                if (deferred == null) {
                    deferred = new int[n];
                }
                deferred[deferredCount++] = start - 1;
            } else if (start == 0) {
                sum += runningPrefix;
            } else {
                sum += runningPrefix - prefix[start - 1];
            }
        }

        for (int j = 0; j < deferredCount; j++) {
            sum -= prefix[deferred[j]];
        }

        return sum;
        
    }
}
