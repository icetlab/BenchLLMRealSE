class Solution {
    public int countTestedDevices(int[] arr) {
        int testedDevices = 0;
        for (int batteryPercentage : arr) {
            if (batteryPercentage > testedDevices) {
                testedDevices++;
            }
        }
        return testedDevices;
    }
}