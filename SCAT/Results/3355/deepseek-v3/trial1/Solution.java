class Solution {
    public int minimumLevels(int[] possible) {
        int n = possible.length;
        int total = 0;
        for (int p : possible) {
            total += (p == 1) ? 1 : -1;
        }
        int alice = 0;
        for (int i = 0; i < n - 1; i++) {
            alice += (possible[i] == 1) ? 1 : -1;
            int bob = total - alice;
            if (alice > bob) {
                return i + 1;
            }
        }
        return -1;
    }
}