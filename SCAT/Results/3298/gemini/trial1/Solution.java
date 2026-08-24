class Solution {
    public int maxSelectedElements(int[] nums) {
         Arrays.sort(nums);

        // dp maps the end of a consecutive sequence to its length.
        java.util.Map<Integer, Integer> dp = new java.util.HashMap<>();
        int maxLen = 0;

        for (int num : nums) {
            // Option 1: Use num as (num + 1). This extends a sequence ending at num.
            int lenUsingNumPlus1 = dp.getOrDefault(num, 0) + 1;
            
            // Option 2: Use num as is. This extends a sequence ending at num - 1.
            int lenUsingNum = dp.getOrDefault(num - 1, 0) + 1;

            // Update lengths for sequences ending at num and num + 1.
            dp.put(num, lenUsingNum);
            dp.put(num + 1, lenUsingNumPlus1);

            // Track the maximum length found so far.
            maxLen = Math.max(maxLen, lenUsingNum);
            maxLen = Math.max(maxLen, lenUsingNumPlus1);
        }

        return maxLen;
    
    }
}