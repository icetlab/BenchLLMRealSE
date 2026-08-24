class Solution {
    public int countSubarrays(int[] nums) {
        int count = 0;
        // Iterate through the array using a sliding window of size 3,
        // which is much more efficient than creating subarray objects.
        for (int i = 0; i <= nums.length - 3; i++) {
            // Use 'long' in the calculation to prevent potential integer overflow.
            if (((long) nums[i] + nums[i + 2]) * 2 == nums[i + 1]) {
                count++;
            }
        }
        return count;
    }
}