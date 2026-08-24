class Solution {
    public int numberOfSpecialChars(String word) {
        int count = 0;
        boolean[] seenLower = new boolean[26];
        boolean[] seenUpper = new boolean[26];
        boolean[] counted = new boolean[26];

        for (char c : word.toCharArray()) {
            if (c < 'A' || (c > 'Z' && c < 'a') || c > 'z') continue;
            int idx = (c <= 'Z') ? (c - 'A') : (c - 'a');

            if (seenUpper[idx]) {
                if (counted[idx]) {
                    count--;
                    counted[idx] = false;
                } else {
                    continue;
                }
            }
            if (c <= 'Z' && seenLower[idx] && !counted[idx]) {
                count++;
                counted[idx] = true;
            }
            if (c <= 'Z') {
                seenUpper[idx] = true;
            } else {
                seenLower[idx] = true;
            }
        }

        return count;
    }
}
