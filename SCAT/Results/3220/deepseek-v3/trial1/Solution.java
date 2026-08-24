class Solution {
    public int countTestedDevices(int[] arr) {
        int tested = 0;
        for (int battery : arr) {
            if (battery - tested > 0) {
                tested++;
            }
        }
        return tested;
    }
}