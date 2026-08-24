class Solution {
    public int minOperations(int[] nums) {
        int f = 0;

        for (int v : nums) {
            if (((v ^ f) & 1) == 0) {
                f++;
            }
        }

        return f;
    }
}