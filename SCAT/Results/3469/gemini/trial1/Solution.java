class Solution {
    public static int helper(int c1, int c2) {
        // This helper calculates the max height assuming odd-numbered rows use `c1` balls
        // and even-numbered rows use `c2` balls.

        // Number of balls for k odd rows (1, 3, 5, ...): k^2
        // Max k_odd such that k_odd^2 <= c1
        long odd_rows_possible = (long) Math.sqrt(c1);

        // Number of balls for k even rows (2, 4, 6, ...): k*(k+1)
        // Max k_even such that k_even*(k_even+1) <= c2
        // This is solved via k_even^2 + k_even - c2 <= 0
        long even_rows_possible = (long) ((-1 + Math.sqrt(1 + 4.0 * c2)) / 2.0);

        // A valid triangle has either k odd and k even rows (height 2k),
        // or k+1 odd and k even rows (height 2k+1).
        if (odd_rows_possible > even_rows_possible) {
            // We can form `even_rows_possible` pairs of (odd, even) rows,
            // and have enough odd-row balls left for one more row.
            return (int) (2 * even_rows_possible + 1);
        } else {
            // We are limited by the number of odd-row balls. We can form
            // `odd_rows_possible` pairs of (odd, even) rows.
            return (int) (2 * odd_rows_possible);
        }
    }
    public int maxHeightOfTriangle(int red, int blue) {
        return Math.max(helper(red,blue),helper(blue,red));
    }
}