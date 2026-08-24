// 1..limit -- n - limit, limit

class Solution {

    private long cnt(int n, int l) {
        if (l > n) l = n;
        if (n > l * 2) return 0;
    //    System.out.println(n + " " + l);
        int b = n - l;
        return l - b + 1;
    }

    public long distributeCandies(int n, int limit) {
        long nn = n;
        long L = limit;
        long a = Math.max(0L, nn - L);
        long b = Math.min(2 * L, nn);
        long res = 0;
        // region1: t in [a..min(b, L)]
        long end1 = Math.min(b, L);
        if (a <= end1) {
            long len1 = end1 - a + 1;
            res += len1 * (a + end1 + 2) / 2;
        }
        // region2: t in [max(a, L+1)..b]
        long start2 = Math.max(a, L + 1);
        if (start2 <= b) {
            long len2 = b - start2 + 1;
            res += len2 * (2 * L + 1) - (start2 + b) * len2 / 2;
        }
        return res;
    }
}