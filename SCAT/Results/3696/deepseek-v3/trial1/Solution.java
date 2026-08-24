class Solution {
    public long countSubstrings(String s) {
        int n = s.length();
        int[] digit = new int[n];
        for (int i = 0; i < n; i++) digit[i] = s.charAt(i) - '0';
        long total = 0;
        for (int last = 1; last <= 9; last++) {
            int[] remCount = new int[last];
            for (int i = 0; i < n; i++) {
                int d = digit[i];
                int[] next = new int[last];
                for (int r = 0; r < last; r++) {
                    if (remCount[r] > 0) {
                        int nr = (r * 10 + d) % last;
                        next[nr] += remCount[r];
                    }
                }
                int startRem = d % last;
                next[startRem]++;
                if (d == last) {
                    total += next[0];
                }
                remCount = next;
            }
        }
        return total;
    }
}
