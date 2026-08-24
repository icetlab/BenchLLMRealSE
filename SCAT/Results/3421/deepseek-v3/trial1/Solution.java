class Solution {
    public int countCompleteDayPairs(int[] hours) {
        int[] remainderCount = new int[24];
        int count = 0;
        for (int h : hours) {
            int remainder = h % 24;
            int complement = (24 - remainder) % 24;
            count += remainderCount[complement];
            remainderCount[remainder]++;
        }
        return count;
    }
}