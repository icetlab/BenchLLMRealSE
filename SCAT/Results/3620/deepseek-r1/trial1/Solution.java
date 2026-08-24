class Solution {
    public int maxDistinctElements(int[] nums, int k) {
        java.util.Arrays.sort(nums);
        long next = Long.MIN_VALUE;
        int count = 0;
        for (int num : nums) {
            long left = (long) num - k;
            long right = (long) num + k;
            long val = Math.max(next, left);
            if (val <= right) {
                count++;
                next = val + 1;
            }
        }
        return count;
    }
}