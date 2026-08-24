class Solution {
    public int maximumEnergy(int[] energy, int k) {
        int n = energy.length;
        int maxEnergy = Integer.MIN_VALUE;

        for (int i = n - 1; i >= n - k && i >= 0; i--) {
            int currentPathSum = 0;
            for (int j = i; j >= 0; j -= k) {
                currentPathSum += energy[j];
                maxEnergy = Math.max(maxEnergy, currentPathSum);
            }
        }
        return maxEnergy;
    }
}