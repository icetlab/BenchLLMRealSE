class Solution {
    public int getLargestOutlier(int[] nums) {
        int sum = 0;
        Map<Integer, Integer> freq = new HashMap<>();
        for (int num : nums) {
            sum += num;
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }

        int result = Integer.MIN_VALUE;
        for (int num : nums) {
            if (num <= result) {
                continue;
            }
            int rest = sum - num;
            if (rest % 2 == 0) {
                int half = rest / 2;
                int count = freq.getOrDefault(half, 0);
                if (count > (half == num ? 1 : 0)) {
                    result = num;
                }
            }
        }

        return result;
    }
}