import java.math.BigInteger;

class Solution {
    public long maximumStrength(int[] nums, int k) {
        int n = nums.length;
        BigInteger[] prev = new BigInteger[n + 1];
        BigInteger[] currRow = new BigInteger[n + 1];
        
        // Initialize prev row (i=0) with zeros
        for (int j = 0; j <= n; j++) {
            prev[j] = BigInteger.ZERO;
        }

        for (int i = 1; i <= k; i++) {
            BigInteger maxsum = BigInteger.valueOf(Long.MIN_VALUE);
            BigInteger curr = BigInteger.valueOf(Long.MIN_VALUE);
            int multiplier = (i % 2 == 1) ? k + 1 - i : i - 1 - k;
            BigInteger multiplierBI = BigInteger.valueOf(multiplier);
            
            for (int j = i - 1; j < n; j++) {
                BigInteger val = BigInteger.valueOf(nums[j]).multiply(multiplierBI);
                curr = curr.add(val).max(prev[j].add(val));
                maxsum = maxsum.max(curr);
                currRow[j + 1] = maxsum;
            }
            
            // Swap rows for next iteration
            BigInteger[] temp = prev;
            prev = currRow;
            currRow = temp;
        }

        return prev[n].longValue();
    }
}