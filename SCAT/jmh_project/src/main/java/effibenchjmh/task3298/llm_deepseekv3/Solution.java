package effibenchjmh.task3298.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int maxSelectedElements(int[] nums) {
         Arrays.sort(nums);

        int n = nums.length;
        Map<Integer, Integer> dp = new HashMap<>();

        for (int j = n - 1; j >= 0; j--) {
            int v = nums[j];
            dp.put(v, Math.max(dp.getOrDefault(v, 0), 1 + dp.getOrDefault(v + 1, 0)));
            dp.put(v + 1, Math.max(dp.getOrDefault(v + 1, 0), 1 + dp.getOrDefault(v + 2, 0)));
        }

        int ans = 0;
        for (int val : dp.values()) {
            ans = Math.max(ans, val);
        }
        return ans;
    
    }
}
