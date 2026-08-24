class Solution {
    public int distributeCandies(int n, int limit) {
        int count = 0;
        for (int i = 0; i <= Math.min(n, limit); i++) {
            for (int j = 0; j <= Math.min(n - i, limit); j++) {
                int k = n - i - j;
                if (k >= 0 && k <= limit) {
                    count++;
                }
            }
        }
        return count;
    }
}