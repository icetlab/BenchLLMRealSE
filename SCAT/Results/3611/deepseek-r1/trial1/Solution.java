class Solution {
    public static int[] minBitwiseArray(List<Integer> nums) {
        int n = nums.size();
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            int num = nums.get(i);
            // All-bits-set case (e.g., 1, 3, 7, 15, ...)
            if ((num & (num + 1)) == 0) {
                ans[i] = (num - 1) / 2;
                continue;
            }
            // Count trailing ones using trailing zeros of the bitwise complement
            int trailingOnes = Integer.numberOfTrailingZeros(~num);
            if (trailingOnes == 0) {
                ans[i] = -1;
                continue;
            }
            // Clear the first zero from LSB and the bit immediately below it
            int mask = (1 << trailingOnes) | (1 << (trailingOnes - 1));
            ans[i] = num & ~mask;
        }
        return ans;
    }
}