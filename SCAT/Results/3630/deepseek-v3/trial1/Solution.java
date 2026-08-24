class Solution {
    long mod = (long)1e9 + 7;
    long[] count = new long[26];//vector A
    long[][] M = new long[26][26];
    long[][] identityMatrix = new long[26][26];
    public int lengthAfterTransformations(String s, int t, List<Integer> nums) {
        fillM(nums);
        for (char ch : s.toCharArray()) {
            count[ch - 'a']++;
        }
        for (int i = 0; i < 26; i++) {
            identityMatrix[i][i] = 1;
        }
        long[][] finalState = fastExp(M, t);

        long ans = 0;
        for (int i = 0; i < 26; i++) {
            long sum = 0;
            for (int j = 0; j < 26; j++) {
                sum = (sum + (count[j] * finalState[j][i]) % mod) % mod;
            }
            ans = (ans + sum) % mod;
        }
        return (int) ans;

    }
    private long[][] fastExp(long[][] M, long t) {
        long[][] result = identityMatrix;
        long[][] base = M;
        while (t > 0) {
            if ((t & 1) == 1) {
                result = multiply(result, base);
            }
            base = multiply(base, base);
            t >>= 1;
        }
        return result;
    }
    private long[][] multiply(long[][] A, long[][] B) {
        long[][] res = new long[26][26];
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                for (int k = 0; k < 26; k++) {
                    res[i][j] = (res[i][j] + (A[i][k] * B[k][j]) % mod) % mod;
                }
            }
        }
        return res;
    }
    private void fillM(List<Integer> nums) {
        for (int i = 0; i < 26; i++) {
            int nextChars = nums.get(i); 
            int idx = (i + 1) % 26;
            for (int j = 0; j < nextChars; j++) {
                M[i][idx] = 1;
                idx = (idx + 1) % 26;
            }
        }
        
    }
}