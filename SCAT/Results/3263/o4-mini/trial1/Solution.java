class Solution {
    public int minimumCost(int[] nums) {
        int first = nums[0];
        int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
        for (int i = 1; i < nums.length; i++) {
            int v = nums[i];
            if (v < min1) {
                min2 = min1;
                min1 = v;
            } else if (v < min2) {
                min2 = v;
            }
        }
        return first + min1 + min2;
    }
}