class Solution {
    public int[] resultsArray(int[] nums, int k) {
        int n = nums.length;
        int[] ans = new int[n - k + 1];
        int count = 1;
        for (int i = 0, f = 0; i < n; i++) {
            if (i > 0 && nums[i] == nums[i - 1] + 1) {
                count++;
            } else {
                count = 1;
            }
            if (i >= k - 1) {
                ans[f++] = count >= k ? nums[i] : -1;
            }
        }
        return ans;
    }
}