class Solution {
    long gcd(long a, long b) {
        if (b==0) return a;
        return gcd(b,a%b);
    }

    // Precomputed LCMs and signs for all non-empty subsets of coins
    private long[] lcms;
    private int[] signs;

    private boolean check(int[] coins, long k, long v) {
        long total = 0;
        for (int mask = 1; mask < lcms.length; mask++) {
            total += signs[mask] * (v / lcms[mask]);
        }
        return total >= k;
    }

    public long findKthSmallest(int[] coins, int k) {
        int n = coins.length;
        // Find smallest coin for an upper bound: the k-th number cannot exceed k * minCoin
        long minCoin = Long.MAX_VALUE;
        for (int c : coins) {
            if (c < minCoin) minCoin = c;
        }
        long h = (long) k * minCoin;

        // Precompute LCM and sign for each subset
        int m = 1 << n;
        lcms = new long[m];
        signs = new int[m];
        for (int mask = 1; mask < m; mask++) {
            long lcm = 1;
            int bits = 0;
            boolean overflow = false;
            for (int j = 0; j < n; j++) {
                if ((mask & (1 << j)) != 0) {
                    bits++;
                    if (lcm == 1) {
                        lcm = coins[j];
                    } else {
                        long g = gcd(lcm, (long) coins[j]);
                        // Check for overflow before multiplying
                        if (lcm / g > Long.MAX_VALUE / coins[j]) {
                            overflow = true;
                            break;
                        }
                        lcm = lcm / g * coins[j];
                    }
                }
            }
            lcms[mask] = overflow ? Long.MAX_VALUE : lcm;
            signs[mask] = (bits % 2 == 0) ? -1 : 1;
        }

        long l = 1;
        while (l <= h) {
            long mid = l + (h - l) / 2;
            if (check(coins, k, mid))
                h = mid - 1;
            else
                l = mid + 1;
        }
        return l;
    }
}