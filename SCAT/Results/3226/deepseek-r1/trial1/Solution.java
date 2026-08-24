class Solution {
    public int[] numberGame(int[] nums) {
        int[] result = nums.clone();
        java.util.Arrays.sort(result);
        for (int i = 0; i < result.length; i += 2) {
            int temp = result[i];
            result[i] = result[i + 1];
            result[i + 1] = temp;
        }
        return result;
    }
}