class Solution {
    public int minimumDifference(int[] nums, int k) {
        int ans = Integer.MAX_VALUE;
        // List of distinct OR values for subarrays ending at current index.
        // Each entry is an int[] {orValue, leftmostStart} where leftmostStart is the smallest
        // start index that yields this OR value for subarrays ending at current index.
        java.util.ArrayList<int[]> prev = new java.util.ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            java.util.ArrayList<int[]> cur = new java.util.ArrayList<>();
            // subarray consisting of only nums[i]
            cur.add(new int[]{x, i});
            for (int[] p : prev) {
                int newOr = p[0] | x;
                int last = cur.get(cur.size() - 1)[0];
                if (newOr != last) {
                    cur.add(new int[]{newOr, p[1]});
                }
            }
            // Now cur contains distinct OR values in decreasing order of start index.
            // For each distinct OR value, the possible subarray ORs are exactly that value.
            // We update answer for each distinct OR.
            for (int[] p : cur) {
                int diff = Math.abs(k - p[0]);
                if (diff < ans) {
                    ans = diff;
                    if (ans == 0) return 0;
                }
            }
            prev = cur;
        }
        return ans;
    }
}