class Solution {
    public int minimumLevels(int[] possible) {
        int n=possible.length;
        int totalSuffix = 0;
        for (int v : possible) {
            totalSuffix += (v == 1 ? 1 : -1);
        }
        int prefix = 0;
        for (int i = 0; i < n - 1; i++) {
            prefix += (possible[i] == 1 ? 1 : -1);
            totalSuffix -= (possible[i] == 1 ? 1 : -1);
            if (prefix > totalSuffix) {
                return i + 1;
            }
        }
        return -1;
    }
}