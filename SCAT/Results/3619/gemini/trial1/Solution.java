class Solution {
    public int maxIncreasingSubarrays(List<Integer> nums) {
        int n = nums.size();
        if (n == 0) {
            return 0;
        }

        int max = 0;
        int prevLength = 0;
        int i = 0;
        while (i < n) {
            int start = i;
            i++;
            while (i < n && nums.get(i) > nums.get(i - 1)) {
                i++;
            }
            int currentLength = i - start;

            max = Math.max(max, currentLength / 2);
            if (prevLength > 0) {
                max = Math.max(max, Math.min(prevLength, currentLength));
            }
            
            prevLength = currentLength;
        }
        return max;
    }
}