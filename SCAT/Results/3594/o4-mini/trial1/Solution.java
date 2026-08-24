class Solution {
    public int getLargestOutlier(int[] nums) {
        long sum = 0;
        Map<Integer, Integer> counts = new HashMap<>();
        for (int num : nums) {
            sum += num;
            counts.put(num, counts.getOrDefault(num, 0) + 1);
        }

        int result = Integer.MIN_VALUE;
        for (int num : nums) {
            if (num <= result) continue;
            long s = sum - num;
            if ((s & 1) == 0) {
                int half = (int)(s / 2);
                Integer cnt = counts.get(half);
                if (cnt != null && (half != num || cnt > 1)) {
                    result = num;
                }
            }
        }

        return result;
    }
}