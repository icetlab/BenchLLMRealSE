package effibenchjmh.task3298.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    public int maxSelectedElements(int[] nums) {
         Arrays.sort(nums);

        int n = nums.length;
        int[] dp = new int[1000002 + 2];  // dp of size 1e6 + 2 (maximum nums[i] is 1e6)

        for (int j = n - 1; j >= 0; j--) {
            dp[nums[j]] = Math.max(dp[nums[j]], 1 + dp[nums[j] + 1]);
            dp[nums[j] + 1] = Math.max(dp[nums[j] + 1], 1 + dp[nums[j] + 2]);
        }

        int ans = 0;
        for (int val : dp) {
            ans = Math.max(ans, val);
        }
        return ans;
    
    }
}
