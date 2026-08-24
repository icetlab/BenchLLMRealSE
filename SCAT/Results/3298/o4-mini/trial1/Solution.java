class Solution {
    public int maxSelectedElements(int[] nums) {
         Arrays.sort(nums);
        int n = nums.length;
        int maxVal = nums[n - 1];
        int[] dp = new int[maxVal + 3];  // dp of size 1e6 + 2 (maximum nums[i] is 1e6)

        int ans = 0;
        for (int j = n - 1; j >= 0; j--) {
            dp[nums[j]] = Math.max(dp[nums[j]], 1 + dp[nums[j] + 1]);
            ans = Math.max(ans, dp[nums[j]]);
            dp[nums[j] + 1] = Math.max(dp[nums[j] + 1], 1 + dp[nums[j] + 2]);
            ans = Math.max(ans, dp[nums[j] + 1]);
        }
        return ans;
    
    }
}