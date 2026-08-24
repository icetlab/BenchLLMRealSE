class Solution {
   public int maxAdjacentDistance(int[] nums) {
		 if (nums == null || nums.length < 2) {
            return 0;
        }

        int maxDistance = 0;
        for (int i = 0; i < nums.length; i++) {
            int distance = Math.abs(nums[i] - nums[(i + 1) % nums.length]);
            maxDistance = Math.max(maxDistance, distance);
        }

        return maxDistance;
    }
}