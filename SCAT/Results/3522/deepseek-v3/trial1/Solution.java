class Solution {
    public int[] resultsArray(int[] nums, int k) {
        int n = nums.length;
        int[] res = new int[n - k + 1];
        if (k == 1) {
            return nums;
        }
        
        // Precompute consecutive difference validity
        boolean[] isConsecutive = new boolean[n - 1];
        for (int i = 0; i < n - 1; i++) {
            isConsecutive[i] = (nums[i] + 1 == nums[i + 1]);
        }
        
        // Sliding window to check if each window is strictly increasing by 1
        int consecutiveCount = 0;
        for (int i = 0; i < n; i++) {
            if (i > 0 && isConsecutive[i - 1]) {
                consecutiveCount++;
            }
            if (i >= k) {
                if (isConsecutive[i - k]) {
                    consecutiveCount--;
                }
            }
            if (i >= k - 1) {
                if (consecutiveCount == k - 1) {
                    res[i - k + 1] = nums[i];
                } else {
                    res[i - k + 1] = -1;
                }
            }
        }
        
        return res;
    }
}