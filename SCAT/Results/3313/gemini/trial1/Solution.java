import java.math.BigInteger;

class Solution {
    public long maximumStrength(int[] nums, int k) {
        long[] dp = new long[nums.length + 1];

        for (int i = 1; i <= k; i++) {
            long[] next_dp = new long[nums.length + 1];
            long maxsum = Long.MIN_VALUE;
            long curr = Long.MIN_VALUE;
            long multiplier = (i % 2 == 1) ? (long) k + 1 - i : (long) i - 1 - k;

            for (int j = i - 1; j < nums.length; j++) {
                long term = (long) nums[j] * multiplier;

                // `curr` is the max strength with `i` subarrays, with the i-th subarray ending at index `j`.
                // This can be formed by either extending the i-th subarray that ends at `j-1` (whose max strength is `curr`),
                // or by starting a new i-th subarray at `j` (max strength of previous `i-1` subarrays is `dp[j]` from the previous iteration).
                // We take the maximum of these two possibilities before adding the current element's contribution.
                // Since `curr` is initialized to Long.MIN_VALUE, `Math.max(curr, dp[j])` correctly selects `dp[j]` for the first element of the subarray.
                curr = Math.max(curr, dp[j]) + term;

                // `maxsum` tracks the maximum strength found so far for `i` subarrays, up to index `j`.
                maxsum = Math.max(maxsum, curr);
                next_dp[j + 1] = maxsum;
            }
            dp = next_dp;
        }

        return dp[nums.length];
    }
}