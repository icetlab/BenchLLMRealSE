class Solution {
    public int maxDifference(String s) {
        HashMap<Character, Integer> map = new HashMap<>();

        // Count frequencies
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }

        int maxOdd = Integer.MIN_VALUE;
        int minEven = Integer.MAX_VALUE;

        // Find maximum odd frequency and minimum even frequency
        for (int freq : map.values()) {
            if (freq % 2 == 0) {
                minEven = Math.min(minEven, freq);
            } else {
                maxOdd = Math.max(maxOdd, freq);
            }
        }

        // If both an odd and an even frequency exist, return their difference
        if (maxOdd != Integer.MIN_VALUE && minEven != Integer.MAX_VALUE) {
            return maxOdd - minEven;
        }
        return 0;
    }
}
