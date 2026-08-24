package effibenchjmh.task3225.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {

    public int maxSubarrayLength(int[] nums, int k) {
        int ans = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        int left = 0;
        for (int right = 0; right < nums.length; right++) {
            map.put(nums[right], map.getOrDefault(nums[right], 0) + 1);

            while (map.get(nums[right]) > k) {
                int count = map.get(nums[left]) - 1;
                if (count == 0)
                    map.remove(nums[left]);
                else
                    map.put(nums[left], count);
                left++;
            }

            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }
}
