package effibenchjmh.task3353.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public boolean isSubstringPresent(String s) {
    if (s.length() < 2) {
        return false;
    }
    boolean[][] seen = new boolean[26][26];
    for (int i = 0; i < s.length() - 1; i++) {
        int c1 = s.charAt(i) - 'a';
        int c2 = s.charAt(i + 1) - 'a';

        // A substring and its reverse are present if we've already seen the reverse
        // of the current substring, or if the current substring is a palindrome.
        if (seen[c2][c1] || c1 == c2) {
            return true;
        }
        
        // Mark the current substring as seen.
        seen[c1][c2] = true;
    }
    return false;
}
}
