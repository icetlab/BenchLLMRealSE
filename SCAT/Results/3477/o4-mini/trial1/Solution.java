class Solution {
    public int minOperations(int[] nums) {
        var f = 0;

        for (var v : nums) {
            if (((v ^ f) & 1) == 0) {
                f++;
            }
        }

        return f;
    }
}