package effibenchjmh.task3225.llm_gemini;

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
        for(int right = 0;right < nums.length;right++) {
            map.put(nums[right], map.getOrDefault(nums[right], 0) + 1);

            while(map.get(nums[right]) > k) {
                map.compute(nums[left], (key, val) -> val == 1 ? null : val - 1);
                left++;
            }

            ans = Math.max(ans, right - left + 1);

        }

        return ans;

    }
}
