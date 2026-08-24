class Solution {
    public int countKConstraintSubstrings(String s, int k) {
        int n = s.length();
        long cntZero = countAtMost(s, k, '0');
        long cntOne = countAtMost(s, k, '1');
        long cntBoth = countAtMostBoth(s, k);
        return (int)(cntZero + cntOne - cntBoth);
    }
    private long countAtMost(String s, int k, char target) {
        int l = 0, n = s.length();
        long count = 0;
        int c = 0;
        for (int r = 0; r < n; r++) {
            if (s.charAt(r) == target) c++;
            while (c > k) {
                if (s.charAt(l) == target) c--;
                l++;
            }
            count += r - l + 1;
        }
        return count;
    }

    private long countAtMostBoth(String s, int k) {
        int l = 0, n = s.length();
        long count = 0;
        int z = 0, o = 0;
        for (int r = 0; r < n; r++) {
            if (s.charAt(r) == '0') z++;
            else o++;
            while (z > k || o > k) {
                if (s.charAt(l) == '0') z--;
                else o--;
                l++;
            }
            count += r - l + 1;
        }
        return count;
    }
}