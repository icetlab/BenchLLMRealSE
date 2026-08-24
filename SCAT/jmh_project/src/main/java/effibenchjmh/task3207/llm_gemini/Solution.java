package effibenchjmh.task3207.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int findMinimumOperations(String s1, String s2, String s3) {
        int l1 = s1.length();
        int l2 = s2.length();
        int l3 = s3.length();

        int minLength = Math.min(l1, Math.min(l2, l3));
        int commonPrefixLength = 0;
        for (int i = 0; i < minLength; i++) {
            if (s1.charAt(i) == s2.charAt(i) && s2.charAt(i) == s3.charAt(i)) {
                commonPrefixLength++;
            } else {
                break;
            }
        }

        if (commonPrefixLength == 0) {
            // If there's no common prefix, it's impossible unless they are already equal.
            // The original code returns 0 if they are already equal (e.g., all empty).
            return s1.equals(s2) && s2.equals(s3) ? 0 : -1;
        }

        // The number of operations is the total characters to remove.
        return (l1 - commonPrefixLength) + (l2 - commonPrefixLength) + (l3 - commonPrefixLength);
    }
}
