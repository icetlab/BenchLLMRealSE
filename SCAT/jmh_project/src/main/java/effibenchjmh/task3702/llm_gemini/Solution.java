package effibenchjmh.task3702.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int maxLength(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }

        int maxLength = 0;

        for (int i = 0; i < n; i++) {
            long product = 1;
            long currentGcd = 0; // gcd(0, x) = x
            long currentLcm = 1; // lcm(1, x) = x

            for (int j = i; j < n; j++) {
                long num = nums[j];

                currentGcd = gcd(currentGcd, num);
                currentLcm = lcm(currentLcm, num);

                // Check for potential product overflow before multiplication.
                if (num != 0 && Long.MAX_VALUE / num < product) {
                    // Product will overflow. The equality check will fail, so we can stop extending this subarray.
                    break;
                }
                product *= num;

                if (product == currentLcm * currentGcd) {
                    maxLength = Math.max(maxLength, j - i + 1);
                }
            }
        }
        return maxLength;
    }

    private boolean isProductEquivalent(int[] arr) {
        if (arr.length == 0) {
            return false;
        }
        long product = 1;
        for (int num : arr) {
            product *= num;
        }
        long gcd = arr[0];
        for (int i = 1; i < arr.length; i++) {
            gcd = gcd(gcd, arr[i]);
        }
        long lcm = arr[0];
        for (int i = 1; i < arr.length; i++) {
            lcm = lcm(lcm, arr[i]);
        }
        return product == lcm * gcd;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private long lcm(long a, long b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        // To prevent overflow, divide before multiplying
        return (a / gcd(a, b)) * b;
    }
}
