class Solution {
    public int numberOfSpecialChars(String word) {
        int lowerSeen = 0;
        int upperSeen = 0;
        int invalid = 0;

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (c >= 'a' && c <= 'z') {
                int bit = 1 << (c - 'a');
                if ((upperSeen & bit) != 0) {
                    invalid |= bit;
                } else {
                    lowerSeen |= bit;
                }
            } else if (c >= 'A' && c <= 'Z') {
                upperSeen |= 1 << (c - 'A');
            }
        }

        return Integer.bitCount(lowerSeen & upperSeen & ~invalid);
    }
}
