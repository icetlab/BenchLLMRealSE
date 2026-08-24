class Solution {
    public int minimumArrayLength(int[] nums) {
        int min = Integer.MAX_VALUE;
        for (int num : nums) {
            min = Math.min(min, num);
        }

        int minCount = 0;
        for (int num : nums) {
            if (num % min != 0) {
                return 1;
            }
            if (num == min) {
                minCount++;
            }
        }

        return (minCount + 1) / 2;
    }
}