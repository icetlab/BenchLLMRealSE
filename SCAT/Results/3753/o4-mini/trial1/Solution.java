class Solution {
    public int maxDifference(String s) {
        int[] freq = new int[26];
        for (int i = 0; i < s.length(); i++) {
            freq[s.charAt(i) - 'a']++;
        }
        int oddMax = Integer.MIN_VALUE;
        int evenMin = Integer.MAX_VALUE;
        for (int f : freq) {
            if (f % 2 == 1) {
                oddMax = Math.max(oddMax, f);
            } else if (f != 0) {
                evenMin = Math.min(evenMin, f);
            }
        }
        return (oddMax == Integer.MIN_VALUE || evenMin == Integer.MAX_VALUE) ? 0 : oddMax - evenMin;
    }
}
