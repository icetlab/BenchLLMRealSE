class Solution {
    public int waysToReachStair(int k) {
        int maxU = 30;
        long[][] C = new long[maxU + 2][maxU + 2];
        for (int n = 0; n <= maxU + 1; n++) {
            C[n][0] = C[n][n] = 1;
            for (int r = 1; r < n; r++) {
                C[n][r] = C[n - 1][r - 1] + C[n - 1][r];
            }
        }
        long ans = (k == 1) ? 1 : 0;
        for (int U = 0; U <= maxU; U++) {
            long pow2 = 1L << U;
            long D = pow2 - k;
            if (D < 0) continue;
            if (U == 0 && D == 0) continue;
            if (D <= U) {
                ans += C[U][(int) D];
            }
            if (D >= 1 && D <= U + 1) {
                ans += C[U][(int) (D - 1)];
            }
        }
        return (int) ans;
    }
}