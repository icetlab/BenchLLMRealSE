class Solution {
    public int maximumLength(String s) {
        java.util.Map<Character, java.util.List<Integer>> groups = new java.util.HashMap<>();
        if (s.isEmpty()) {
            return -1;
        }

        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            int j = i;
            while (j < s.length() && s.charAt(j) == c) {
                j++;
            }
            int length = j - i;
            groups.computeIfAbsent(c, k -> new java.util.ArrayList<>()).add(length);
            i = j;
        }

        int maxLength = 0;

        for (java.util.List<Integer> lengths : groups.values()) {
            lengths.sort(java.util.Collections.reverseOrder());

            // Case 1: From one long group. A group of length k can provide 3 occurrences
            // of a special string of length k-2.
            if (lengths.get(0) >= 3) {
                maxLength = Math.max(maxLength, lengths.get(0) - 2);
            }

            // Case 2: From two groups. With top two lengths k1 >= k2, we can get one
            // occurrence from the k2-group and two from the k1-group. This is possible
            // for a special string of length min(k1-1, k2).
            if (lengths.size() >= 2) {
                int k1 = lengths.get(0);
                int k2 = lengths.get(1);
                maxLength = Math.max(maxLength, Math.min(k1 - 1, k2));
            }

            // Case 3: From three groups. We can take one occurrence from each of the
            // top three groups. The length is limited by the third longest group.
            if (lengths.size() >= 3) {
                maxLength = Math.max(maxLength, lengths.get(2));
            }
        }

        return maxLength > 0 ? maxLength : -1;
    }
}