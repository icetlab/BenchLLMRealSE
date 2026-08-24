class Solution {
    public int minZeroArray(int[] nums, int[][] queries) {
        int left = 0, right = queries.length;
        int result = -1;
        long[] diff = new long[nums.length + 1];

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (canMakeZero(nums, queries, mid, diff)) {
                result = mid;
                right = mid - 1; // try smaller k
            } else {
                left = mid + 1; // need more queries
            }
        }

        return result;
    }

    private boolean canMakeZero(int[] nums, int[][] queries, int k, long[] diff) {
        int n = nums.length;
        // reset difference array
        for (int i = 0; i <= n; i++) diff[i] = 0;

        for (int i = 0; i < k; i++) {
            int l = queries[i][0];
            int r = queries[i][1];
            int val = queries[i][2];

            diff[l] += val;
            if (r + 1 < n) {
                diff[r + 1] -= val;
            }
        }

        long current = 0;
        for (int i = 0; i < n; i++) {
            current += diff[i];
            if (nums[i] > current) {
                return false;
            }
        }

        return true;
    }
}