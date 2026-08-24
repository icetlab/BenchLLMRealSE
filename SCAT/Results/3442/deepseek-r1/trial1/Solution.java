class Solution {
    public int maxTotalReward(int[] arr) {
        if (arr.length == 0) return 0;
        java.util.Arrays.sort(arr);
        int max = arr[arr.length - 1];
        if (max == 0) return 0;
        boolean[] dp = new boolean[2 * max];
        dp[0] = true;
        for (int i = 0; i < arr.length; i++) {
            if (i > 0 && arr[i] == arr[i - 1]) continue;
            int v = arr[i];
            for (int s = v - 1; s >= 0; s--) {
                if (dp[s]) {
                    dp[s + v] = true;
                }
            }
        }
        for (int s = dp.length - 1; s >= 0; s--) {
            if (dp[s]) return s;
        }
        return 0;
    }
}