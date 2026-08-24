class Solution {
    public int[] minBitwiseArray(List<Integer> nums) {
        int n = nums.size();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            int x = nums.get(i);
            if ((x & 1) == 0) {
                arr[i] = -1;
            } else {
                int trailingOnes = Integer.numberOfTrailingZeros(x + 1);
                arr[i] = x - (1 << (trailingOnes - 1));
            }
        }
        return arr;
    }
}