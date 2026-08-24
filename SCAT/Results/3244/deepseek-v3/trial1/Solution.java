class Solution {
    public int minimumArrayLength(int[] nums) {
        int min = Integer.MAX_VALUE;
        int minCount = 0;
        boolean hasNonDivisible = false;

        for (int num : nums) {
            if (num < min) {
                min = num;
                minCount = 1;
                hasNonDivisible = false;
            } else if (num == min) {
                minCount++;
            } else if (num % min != 0) {
                hasNonDivisible = true;
            }
        }

        if (minCount == 1 || hasNonDivisible) {
            return 1;
        }
        
        return (minCount + 1) / 2;
    }
}