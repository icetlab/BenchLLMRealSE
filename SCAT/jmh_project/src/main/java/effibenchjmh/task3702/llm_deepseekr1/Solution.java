package effibenchjmh.task3702.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int maxLength(int[] nums) {
        int n = nums.length;
        int zeroCount = 0;
        int maxVal = 0;
        for (int v : nums) {
            if (v == 0) {
                zeroCount++;
            }
            if (v > maxVal) {
                maxVal = v;
            }
        }

        if (zeroCount == 1) {
            return n;
        }
        if (n <= 1) {
            return (n == 1 && nums[0] == 1) ? 1 : 0;
        }

        java.util.List<Integer> primes = sievePrimes((int) Math.sqrt(maxVal) + 1);

        java.util.List<java.util.List<Integer>> factors = new java.util.ArrayList<>(n);
        java.util.Map<Integer, java.util.List<Integer>> factorCache = new java.util.HashMap<>();
        for (int i = 0; i < n; i++) {
            int v = nums[i];
            java.util.List<Integer> list = factorCache.get(v);
            if (list == null) {
                list = factorDistinctPrimes(v, primes);
                factorCache.put(v, list);
            }
            factors.add(list);
        }

        java.util.Map<Integer, Integer> primeCount = new java.util.HashMap<>();
        int conflicts = 0;
        int left = 0;
        int best = 2;

        for (int right = 0; right < n; right++) {
            for (int p : factors.get(right)) {
                int c = primeCount.getOrDefault(p, 0) + 1;
                primeCount.put(p, c);
                if (c == 2) {
                    conflicts++;
                }
            }

            while (conflicts > 0) {
                for (int p : factors.get(left)) {
                    int c = primeCount.get(p);
                    if (c == 2) {
                        conflicts--;
                    }
                    if (c == 1) {
                        primeCount.remove(p);
                    } else {
                        primeCount.put(p, c - 1);
                    }
                }
                left++;
            }

            int len = right - left + 1;
            if (len > best) {
                best = len;
            }
        }

        return best;
    }

    private java.util.List<Integer> sievePrimes(int limit) {
        boolean[] isComposite = new boolean[limit + 1];
        java.util.List<Integer> primes = new java.util.ArrayList<>();
        for (int i = 2; i <= limit; i++) {
            if (!isComposite[i]) {
                primes.add(i);
                if ((long) i * i <= limit) {
                    for (int j = i * i; j <= limit; j += i) {
                        isComposite[j] = true;
                    }
                }
            }
        }
        return primes;
    }

    private java.util.List<Integer> factorDistinctPrimes(int x, java.util.List<Integer> primes) {
        java.util.List<Integer> res = new java.util.ArrayList<>();
        if (x <= 1) {
            return res;
        }
        int t = x;
        for (int p : primes) {
            if ((long) p * p > t) {
                break;
            }
            if (t % p == 0) {
                res.add(p);
                while (t % p == 0) {
                    t /= p;
                }
            }
        }
        if (t > 1) {
            res.add(t);
        }
        return res;
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
