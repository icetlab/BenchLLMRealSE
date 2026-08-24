class Solution {
    public int[] numberGame(int[] nums) {
        int[] arr = nums.clone();
        java.util.Arrays.sort(arr);
        for (int i = 0; i < arr.length; i += 2) {
            // Swap adjacent elements
            int temp = arr[i];
            arr[i] = arr[i + 1];
            arr[i + 1] = temp;
        }
    return arr;
    }
}