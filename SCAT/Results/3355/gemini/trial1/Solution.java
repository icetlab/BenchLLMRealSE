class Solution {
    public int minimumLevels(int[] possible) {
        int totalScore = 0;
        for (int p : possible) {
            totalScore += (p == 1 ? 1 : -1);
        }

        int n = possible.length;
        int aliceScore = 0;
        for (int i = 0; i < n - 1; i++) {
            aliceScore += (possible[i] == 1 ? 1 : -1);
            if (2 * aliceScore > totalScore) {
                return i + 1;
            }
        }

        return -1;
    }
}