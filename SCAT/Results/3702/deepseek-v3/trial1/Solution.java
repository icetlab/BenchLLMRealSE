class Solution {
    public int maxLength(int[] nums) {
        int maxLength = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            long product = 1;
            long gcdVal = 0;
            long lcmVal = 1;
            for (int j = i; j < n; j++) {
                long num = nums[j];
                product *= num;
                gcdVal = (gcdVal == 0) ? num : gcd(gcdVal, num);
                lcmVal = lcm(lcmVal, num);
                if (product == lcmVal * gcdVal) {
                    maxLength = Math.max(maxLength, j - i + 1);
                }
            }
        }
        return maxLength;
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