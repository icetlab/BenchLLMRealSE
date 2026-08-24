package effibenchjmh.task3702;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int maxLength(int[] nums) {
        int n = nums.length;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            java.math.BigInteger prod = java.math.BigInteger.ONE;
            int currGcd = 0;
            int currLcm = 1;
            for (int j = i; j < n; j++) {
                prod = prod.multiply(java.math.BigInteger.valueOf(nums[j]));
                if (j == i) {
                    currGcd = nums[j];
                    currLcm = nums[j];
                } else {
                    currGcd = gcd(currGcd, nums[j]);
                    currLcm = lcm(currLcm, nums[j]);
                }
                if (prod.equals(java.math.BigInteger.valueOf((long) currGcd * currLcm))) {
                    ans = Math.max(ans, j - i + 1);
                }
            }
        }
        return ans;
    }
    private int gcd(int a, int b) {
        while (b != 0) {
            int tmp = a % b;
            a = b;
            b = tmp;
        }
        return a;
    }
    private int lcm(int a, int b) {
        return (a / gcd(a, b)) * b;
    }
}
