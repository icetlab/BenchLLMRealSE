class Solution {
    public long maximumStrength(int[] nums, int k) {
        int n = nums.length;
        long[] prev = new long[n + 1];
        long[] cur = new long[n + 1];

        for (int i = 1; i <= k; i++) {
            int multiplier = (i % 2 == 1) ? k + 1 - i : i - 1 - k;
            int first = i - 1;

            long val = (long) nums[first] * multiplier;
            long curr = prev[first] + val;
            long maxsum = curr;
            cur[first + 1] = maxsum;

            for (int j = i; j < n; j++) {
                val = (long) nums[j] * multiplier;
                long start = prev[j] + val;
                curr = Math.max(curr + val, start);
                if (curr > maxsum) {
                    maxsum = curr;
                }
                cur[j + 1] = maxsum;
            }

            long[] tmp = prev;
            prev = cur;
            cur = tmp;
        }

        return prev[n];
    }
}