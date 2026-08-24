class Solution {
    public int maxDistinctElements(int[] nums, int k) {
        List<int[]> ranges = new ArrayList<>();
        for (int num : nums) {
            ranges.add(new int[]{num - k, num + k});
        }
        ranges.sort(Comparator.comparingInt(a -> a[0]));
        int count = 0;
        int cnt = Integer.MIN_VALUE; 
        for (int[] range : ranges) {
            int start = range[0], end = range[1];
            int val = Math.max(cnt, start);
            if (val <= end) {
                count++;
                cnt = val + 1;
            }
        }
        return count;
    }
}