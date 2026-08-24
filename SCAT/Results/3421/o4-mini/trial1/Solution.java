class Solution {
    public int countCompleteDayPairs(int[] hours) {
        int[] cnt = new int[24];
        int count = 0;
        for (int h : hours) {
            int r = h % 24;
            int complement = (24 - r) % 24;
            count += cnt[complement];
            cnt[r]++;
        }
        return count;
    }
}