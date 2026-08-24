package effibenchjmh.task3702.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int maxLength(int[] nums) {
        int maxLength = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                int[] subarray = new int[j - i + 1];
                for (int k = i; k <= j; k++) {
                    subarray[k - i] = nums[k];
                }
                if (isProductEquivalent(subarray)) {
                    maxLength = Math.max(maxLength, subarray.length);
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
        return (a * b) / gcd(a, b);
    }
}
