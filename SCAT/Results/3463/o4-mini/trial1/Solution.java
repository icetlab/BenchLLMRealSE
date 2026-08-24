class Solution {
    public int numberOfAlternatingGroups(int[] colors) {
        int n = colors.length;
        if (n < 3) return 0;
        int count = 0;
        for (int i = 0; i < n; i++) {
            int a = colors[i];
            int b = colors[(i + 1) % n];
            int c = colors[(i + 2) % n];
            if (a == c && a != b) {
                count++;
            }
        }
        return count;
    }
}