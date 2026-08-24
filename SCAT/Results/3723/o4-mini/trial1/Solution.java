class Solution {
    public int sumOfGoodNumbers(int[] nums, int k) {
        int n = nums.length, sum = 0;
        for (int i = 0; i < n; i++) {
            int left = i - k, right = i + k;
            if (left >= 0 && right < n) {
                if (nums[left] < nums[i] && nums[right] < nums[i]) {
                    sum += nums[i];
                }
            } else if (left >= 0) {
                if (nums[left] < nums[i]) {
                    sum += nums[i];
                }
            } else if (right < n && nums[right] < nums[i]) {
                sum += nums[i];
            }
        }
        
        return sum;
    }
}