class Solution {
        // Helper to calculate C(m, 2) = m * (m - 1) / 2 for m >= 2
    private long combinations(int m) {
        if (m < 2) {
            return 0;
        }
        return (long) m * (m - 1) / 2;
    }

    public int distributeCandies(int n, int limit) {
                // Using combinatorics and the Principle of Inclusion-Exclusion for an O(1) solution.
        // Total ways to solve x1+x2+x3=n is C(n+2, 2).
        long v1 = combinations(n + 2);

        // Subtract cases where one child has > limit candies.
        long v2 = 3 * combinations(n - limit + 1);

        // Add back cases where two children have > limit candies.
        long v3 = 3 * combinations(n - 2 * limit);

        // Subtract cases where three children have > limit candies.
        long v4 = combinations(n - 3 * limit - 1);

        return (int) (v1 - v2 + v3 - v4);
    }
}