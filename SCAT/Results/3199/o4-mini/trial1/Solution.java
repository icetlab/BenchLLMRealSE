class Solution {
    public int distributeCandies(int n, int limit) {
        if ((long)n > 3L * limit) {
            return 0;
        }
        long s = n, m = limit;
        long res;
        if (s <= m) {
            // all sums below or equal to limit: C(s+2, 2)
            res = (s + 2) * (s + 1) / 2;
        } else if (s <= 2 * m) {
            // subtract configurations where one variable > limit
            res = (s + 2) * (s + 1) / 2 - 3 * (s - m + 1) * (s - m) / 2;
        } else {
            // add back where two variables > limit
            res = (s + 2) * (s + 1) / 2
                - 3 * (s - m + 1) * (s - m) / 2
                + 3 * (s - 2 * m) * (s - 2 * m - 1) / 2;
        }
        return (int) res;
    }
}