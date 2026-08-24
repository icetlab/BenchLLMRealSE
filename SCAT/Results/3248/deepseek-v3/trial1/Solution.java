class Solution {
    public long incremovableSubarrayCount(int[] nums) {
        int[] suffix = new int[nums.length];
        int suffixLen = 0;
        suffix[suffixLen++] = nums[nums.length - 1];
        for (int i = nums.length - 2; i >= 0; --i) {
            if (nums[i] >= nums[i + 1]) break;
            suffix[suffixLen++] = nums[i];
        }
        // Reverse suffix in place to make it ascending.
        for (int i = 0, j = suffixLen - 1; i < j; ++i, --j) {
            int tmp = suffix[i];
            suffix[i] = suffix[j];
            suffix[j] = tmp;
        }
        if (nums.length == suffixLen) {
            return ((long) nums.length * (nums.length + 1) / 2);
        }

        long ans = 1 + suffixLen;
        int n = nums.length - suffixLen;
        int last = -1;
        for (int i = 0; i < n; ++i) {
            if (nums[i] <= last) break;
            last = nums[i];
            ans += suffixLen - upperBound(suffix, suffixLen, nums[i]) + 1;
        }
        return ans;
    }

    private int upperBound(int[] arr, int len, int target) {
        int left = 0, right = len;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] > target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
}