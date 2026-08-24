class Solution {
    public int maxDifference(String s) {
        HashMap<Character, Integer> map = new HashMap<>();

        // Count frequencies
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }

        int maxOdd = Integer.MIN_VALUE;
        int minEven = Integer.MAX_VALUE;

        // Find max odd frequency and min even frequency in one pass
        for (int freq : map.values()) {
            if (freq % 2 != 0) {
                if (freq > maxOdd) maxOdd = freq;
            } else {
                if (freq < minEven) minEven = freq;
            }
        }

        // Return 0 if either an odd frequency or an even frequency is missing
        if (maxOdd == Integer.MIN_VALUE || minEven == Integer.MAX_VALUE) return 0;
        return maxOdd - minEven;
    }
}
