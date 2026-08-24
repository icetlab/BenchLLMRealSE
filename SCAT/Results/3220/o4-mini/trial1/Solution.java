class Solution {
    public int countTestedDevices(int[] arr) {
        
        int positiveCount = 0;
        int ans = 0;
        for (int a : arr) {
            if (a > positiveCount) {
                ans++;
                positiveCount++;
            }
        }
        return ans;
    }
}