class Solution {
    public int countSubarrays(int[] nums) {
        int sum = 0;
        for (int i = 0; i + 2 < nums.length; i++) {
            if ((nums[i] + nums[i + 2]) * 2 == nums[i + 1]) {
                sum++;
            }
        }
        return sum;
    }
}