import java.util.Arrays;

class Solution {
    public boolean canSortArray(int[] nums) {
        // Make a sorted copy and compare bit‐counts
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        for (int i = 0; i < nums.length; i++) {
            if (Integer.bitCount(nums[i]) != Integer.bitCount(sorted[i])) {
                return false;
            }
        }
        return true;
    }
}