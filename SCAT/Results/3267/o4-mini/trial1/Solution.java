import java.util.*;

class Solution {
    public int maximumLength(String s) {
        int result = -1;
        int n = s.length();
        List<Integer>[] runs = new List[26];
        for (int idx = 0; idx < 26; idx++) runs[idx] = new ArrayList<>();
        int i = 0;
        while (i < n) {
            char c = s.charAt(i);
            int j = i + 1;
            while (j < n && s.charAt(j) == c) j++;
            runs[c - 'a'].add(j - i);
            i = j;
        }
        for (int k = 0; k < 26; k++) {
            List<Integer> list = runs[k];
            if (list.isEmpty()) continue;
            int maxRun = 0;
            long total = 0;
            for (int len : list) {
                maxRun = Math.max(maxRun, len);
                total += len;
            }
            if (total < 3) continue;
            int low = 1, high = maxRun, best = 0;
            while (low <= high) {
                int mid = (low + high) / 2;
                long occ = 0;
                for (int len : list) {
                    if (len >= mid) {
                        occ += len - mid + 1;
                    }
                    if (occ >= 3) break;
                }
                if (occ >= 3) {
                    best = mid;
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            if (best > 0) result = Math.max(result, best);
        }
        return result;
    }

    private boolean isSpecial(String str) {
        char firstChar = str.charAt(0);
        for (char c : str.toCharArray()) {
            if (c != firstChar) {
                return false;
            }
        }
        return true;
    }

    private int countOverlappingOccurrences(String s, String sub) {
        int count = 0;
        for (int i = 0; i <= s.length() - sub.length(); i++) {
            if (s.substring(i, i + sub.length()).equals(sub)) {
                count++;
            }
        }
        return count;
    }
}