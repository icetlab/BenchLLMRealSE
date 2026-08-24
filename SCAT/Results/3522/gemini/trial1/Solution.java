class Solution {
    public int[] resultsArray(int[] nums, int k) {
        
        int res[]=new int[nums.length-k+1];
        if(k==1)
        {
            return nums;
        }
        // prefixBreaks[i] stores the number of non-consecutive pairs in nums[0...i-1].
        int[] prefixBreaks = new int[nums.length];
        for (int i = 0; i < nums.length - 1; i++) {
            prefixBreaks[i + 1] = prefixBreaks[i] + (nums[i + 1] == nums[i] + 1 ? 0 : 1);
        }

        for (int i = 0; i < res.length; i++) {
            // Number of breaks in the window nums[i...i+k-1]
            // corresponds to checking pairs from index i to i+k-2.
            int breaksInWindow = prefixBreaks[i + k - 1] - prefixBreaks[i];
            
            if (breaksInWindow == 0) {
                res[i] = nums[i + k - 1];
            } else {
                res[i] = -1;
            }
        }

        
        return res;
    }
}