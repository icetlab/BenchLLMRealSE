class Solution {
    public int maxDistinctElements(int[] nums, int k) {
        java.util.Arrays.sort(nums);
        int count = 0;
        long nextAvailable = Long.MIN_VALUE;
        for (int num : nums) {
            long start = (long) num - k;
            long end = (long) num + k;
            long val = Math.max(nextAvailable, start);
            if (val <= end) {
                count++;
                nextAvailable = val + 1;
            }
        }
        return count;
    }
}