class Solution {
    public int subarraySum(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }

        long[] prefix = new long[n];
        prefix[0] = nums[0];

        // The sum for i=0 is always prefix[0] because start will be 0.
        long sum = prefix[0];

        // Calculate prefix sums and the total sum in a single pass.
        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] + nums[i];
            int start = Math.max(0, i - nums[i]);
            if (start == 0) {
                sum += prefix[i];
            } else {
                sum += prefix[i] - prefix[start - 1];
            }
        }

        return (int) sum;
    }
}