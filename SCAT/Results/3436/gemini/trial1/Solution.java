class Solution {
    public int minimumDifference(int[] nums, int k) {
        int ans = Integer.MAX_VALUE;
        // This set stores the bitwise OR of all subarrays ending at the previous element.
        // The number of distinct values is small (at most ~30), because ORing with a new
        // number can only set more bits, and there are a limited number of bits.
        java.util.Set<Integer> prevOrs = new java.util.HashSet<>();

        for (int num : nums) {
            // This set will store the bitwise OR of all subarrays ending at the current element.
            java.util.Set<Integer> currentOrs = new java.util.HashSet<>();
            
            // A subarray of just the current element.
            currentOrs.add(num);
            
            // Extend subarrays ending at the previous element with the current element.
            for (int prevOr : prevOrs) {
                currentOrs.add(prevOr | num);
            }
            
            // Check all new OR values against k.
            for (int orVal : currentOrs) {
                ans = Math.min(ans, Math.abs(orVal - k));
            }
            
            if (ans == 0) {
                return 0;
            }
            
            // The current set becomes the previous set for the next iteration.
            prevOrs = currentOrs;
        }
        
        return ans;
    }
}