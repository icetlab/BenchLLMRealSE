class Solution {
    public int maxIncreasingSubarrays(List<Integer> nums) {
        int n = nums.size();
        if (n == 0) return 0;
        int i = 1;
        int currLen = 1;
        // first increasing segment
        while (i < n && nums.get(i) > nums.get(i - 1)) {
            currLen++;
            i++;
        }
        int prevLen = currLen;
        int max = prevLen / 2;
        // process remaining segments
        while (i < n) {
            currLen = 1;
            i++;
            while (i < n && nums.get(i) > nums.get(i - 1)) {
                currLen++;
                i++;
            }
            max = Math.max(max, currLen / 2);
            max = Math.max(max, Math.min(prevLen, currLen));
            prevLen = currLen;
        }
        return max;
    }
}