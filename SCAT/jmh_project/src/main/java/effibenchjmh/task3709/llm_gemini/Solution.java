package effibenchjmh.task3709.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public boolean hasSpecialSubstring(String s, int k) {
        if (s == null || s.isEmpty() || k <= 0) {
            return false;
        }

        int count = 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                count++;
            } else {
                if (count == k) {
                    return true;
                }
                count = 1;
            }
        }

        return count == k;
    }
}
