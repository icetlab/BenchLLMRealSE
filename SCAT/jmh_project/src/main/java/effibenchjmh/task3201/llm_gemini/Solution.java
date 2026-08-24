package effibenchjmh.task3201.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

// 1..limit -- n - limit, limit

class Solution {

    private long combinations(int k) {
        if (k < 2) {
            return 0;
        }
        return (long) k * (k - 1) / 2;
    }

    public long distributeCandies(int n, int limit) {
        // Using the Principle of Inclusion-Exclusion for an O(1) solution.

        // Total ways to distribute n candies to 3 children without a limit:
        // C(n + 3 - 1, 3 - 1) = C(n + 2, 2)
        long totalWays = combinations(n + 2);

        // Cases where at least one child gets more than 'limit' candies:
        // 3 * C(n - (limit + 1) + 2, 2) = 3 * C(n - limit + 1, 2)
        long oneChildExceeds = combinations(n - limit + 1);

        // Cases where at least two children get more than 'limit' candies:
        // 3 * C(n - 2*(limit + 1) + 2, 2) = 3 * C(n - 2*limit, 2)
        long twoChildrenExceed = combinations(n - 2 * limit);

        // Cases where all three children get more than 'limit' candies:
        // 1 * C(n - 3*(limit + 1) + 2, 2) = C(n - 3*limit - 1, 2)
        long threeChildrenExceed = combinations(n - 3 * limit - 1);

        return totalWays - 3 * oneChildExceeds + 3 * twoChildrenExceed - threeChildrenExceed;
    }
}
