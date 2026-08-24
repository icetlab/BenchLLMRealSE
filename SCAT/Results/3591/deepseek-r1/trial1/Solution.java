class Solution {
    public long shiftDistance(String s, String t, int[] nextCost, int[] previousCost) {
        // Precompute forward costs
        long[][] straight = new long[26][26];
        for (int start = 0; start < 26; start++) {
            long sum = 0;
            for (int step = 0; step < 25; step++) {
                sum += nextCost[(start + step) % 26];
                int dest = (start + step + 1) % 26;
                straight[start][dest] = sum;
            }
        }

        // Precompute reverse costs
        long[][] reverse = new long[26][26];
        for (int start = 0; start < 26; start++) {
            long sum = 0;
            int idx = (start + 1) % 26;
            for (int step = 0; step < 26; step++) {
                sum += previousCost[idx];
                reverse[start][idx] = sum;
                idx = (idx + 1) % 26;
            }
        }

        long mainSum = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != t.charAt(i)) {
                int src = s.charAt(i) - 'a';
                int dst = t.charAt(i) - 'a';
                mainSum += Math.min(straight[src][dst], reverse[dst][src]);
            }
        }
        return mainSum;
    }
}