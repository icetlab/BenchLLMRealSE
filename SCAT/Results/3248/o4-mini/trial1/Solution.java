class Solution {
    public long incremovableSubarrayCount(int[] nums) {
        int n = nums.length;
        int suffixStart = n - 1;
        while (suffixStart > 0 && nums[suffixStart - 1] < nums[suffixStart]) {
            suffixStart--;
        }
        int suffixLen = n - suffixStart;
        if (suffixStart == 0) return ((long) n * (n + 1) / 2);
        int[] suffix = new int[suffixLen];
        System.arraycopy(nums, suffixStart, suffix, 0, suffixLen);
        long ans = 1 + suffixLen;
        int prefixLen = suffixStart;
        int last = -1;
        for (int i = 0; i < prefixLen; ++i) {
            if (nums[i] <= last) break;
            last = nums[i];
            ans += suffixLen - upperBoundInt(suffix, nums[i]) + 1;
        }
        return ans;
    }

    private int upperBoundInt(int[] arr, int target) {
        int left = 0, right = arr.length;
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