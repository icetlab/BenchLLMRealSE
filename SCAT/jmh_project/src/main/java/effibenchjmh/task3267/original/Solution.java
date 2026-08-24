package effibenchjmh.task3267.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int maximumLength(String s) {
        int maxLength = -1;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j <= s.length(); j++) {
                String sub = s.substring(i, j);
                if (isSpecial(sub)) {
                    int occurrences = countOverlappingOccurrences(s, sub);
                    if (occurrences >= 3) {
                        maxLength = Math.max(maxLength, sub.length());
                    }
                }
            }
        }
        return maxLength;
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
