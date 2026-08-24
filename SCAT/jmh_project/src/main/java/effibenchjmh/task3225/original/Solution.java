package effibenchjmh.task3225.original;

import java.util.*;
import java.util.stream.*;

class Solution {

    public boolean isGood(HashMap<Integer, Integer> map, int k) {
        for (int val : map.values())
            if (val > k)
                return false;
        return true;
    }

    public int maxSubarrayLength(int[] nums, int k) {
        int ans = Integer.MIN_VALUE;
        HashMap<Integer, Integer> map = new HashMap<>();
        int left = 0;
        for(int right = 0;right < nums.length;right++) {
            map.put(nums[right], map.getOrDefault(nums[right], 0) + 1);

            while(map.get(nums[right]) > k) {
                map.put(nums[left], map.get(nums[left]) - 1);
                if ((int) map.get(nums[left]) == 0)
                    map.remove(nums[left]);
                left++;
            }

            ans = Math.max(ans, right - left + 1);

        }

        return ans;

    }
}
