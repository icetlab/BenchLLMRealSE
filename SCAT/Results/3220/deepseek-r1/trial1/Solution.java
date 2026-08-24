class Solution {
    public int countTestedDevices(int[] arr) {
        
        int ans = 0;
        for (int i = 0; i < arr.length; ++i) {
            if (arr[i] > ans) {
                arr[i] -= ans;
                ++ans;
            } else {
                arr[i] = 0;
            }
        }
        return ans;
    }
}