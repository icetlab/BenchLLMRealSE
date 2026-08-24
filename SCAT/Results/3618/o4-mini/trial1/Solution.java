class Solution {
    private final long mod = 1000000007L;

    public int possibleStringCount(String word, int k) {
        int len = word.length();
        int[] segments = new int[len];
        int segCount = 0;
// !!!!
// !!!!!! set to the first char
        char curr = word.charAt(0);
        int count = 1;
        for (int i = 1; i < len; i++) {
            char c = word.charAt(i);
            if (c == curr) count++;
            else {
                segments[segCount++] = count;
                curr = c;
                count = 1;
            }
        }
        segments[segCount++] = count;

        // case 1
        if (word.length() < k) return 0;

        // case 2
        long res = 1L;
        if (segCount >= k) {
            for (int i = 0; i < segCount; i++) {
                res = (res * segments[i]) % mod;
            }
            return (int) res;
        }
        
        // case 3
        long[] preSum = new long[k+1];
        Arrays.fill(preSum, 1L);
        for (int idx = 0; idx < segCount; idx++) {
            int itemCnt = segments[idx];
            long[] cnt = new long[k+1];

            for (int currLen=k; currLen>=1; currLen--) {
                long duration = preSum[currLen-1] - (currLen-itemCnt <= 0 ? 0 : preSum[currLen-itemCnt-1]);
                duration = (duration + mod) % mod;
                cnt[currLen] += duration;
                cnt[currLen] %= mod;
            }

            for (int currLen=k; currLen>=0; currLen--) {
                long prevCnt = (preSum[currLen] - (currLen == 0 ? 0 : preSum[currLen-1]) + mod) % mod;
                cnt[k] += 1L * prevCnt * Math.max(0, currLen + itemCnt - k) % mod;
                cnt[k] = cnt[k] % mod;
            }

            long[] newPreSum = new long[k+1];
            for (int i=1; i<=k; i++) {
                newPreSum[i] = (newPreSum[i-1] + cnt[i]) % mod;
            }
            preSum = newPreSum;
        }

        return (int) ((preSum[k] - preSum[k-1] + mod) % mod);
    }
}