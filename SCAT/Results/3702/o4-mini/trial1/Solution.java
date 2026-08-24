import java.util.*;
class Solution {
    public int maxLength(int[] nums) {
        int n = nums.length;
        if (n < 2) return 0;
        int res = 0;
        Map<Integer, Integer> primeToIndex = new HashMap<>();
        Map<Integer, List<Integer>> memo = new HashMap<>();
        int l = 0;
        for (int r = 0; r < n; r++) {
            int x = nums[r];
            List<Integer> primes = memo.computeIfAbsent(x, key -> factor(key));
            for (int p : primes) {
                if (primeToIndex.containsKey(p)) {
                    l = Math.max(l, primeToIndex.get(p) + 1);
                }
                primeToIndex.put(p, r);
            }
            int len = r - l + 1;
            if (len >= 2) res = Math.max(res, len);
        }
        return Math.max(res, 2);
    }

    private List<Integer> factor(int num) {
        List<Integer> primes = new ArrayList<>();
        if (num % 2 == 0) {
            primes.add(2);
            while (num % 2 == 0) num /= 2;
        }
        for (int d = 3; d * d <= num; d += 2) {
            if (num % d == 0) {
                primes.add(d);
                while (num % d == 0) num /= d;
            }
        }
        if (num > 1) primes.add(num);
        return primes;
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