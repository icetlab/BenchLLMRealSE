// Counts ordered triples (a,b,c) with a+b+c=n and 0 <= a,b,c <= limit.
// Uses inclusion-exclusion to compute the answer in O(1).
class Solution {

    private long comb2(long x) {
        return x * (x - 1) / 2;
    }

    public long distributeCandies(int n, int limit) {
        if (n < 0 || limit < 0) {
            return 0L;
        }
        if (n > 3L * limit) {
            return 0L;
        }

        long t = limit + 1L;
        long ans = comb2(n + 2L);

        if (n >= t) {
            ans -= 3L * comb2(n - t + 2L);
        }
        if (n >= 2L * t) {
            ans += 3L * comb2(n - 2L * t + 2L);
        }
        if (n >= 3L * t) {
            ans -= comb2(n - 3L * t + 2L);
        }

        return ans;
    }
}