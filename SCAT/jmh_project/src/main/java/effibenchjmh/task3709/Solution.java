package effibenchjmh.task3709;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public boolean hasSpecialSubstring(String s, int k) {
        int n = s.length();
        for (int i = 0; i <= n - k; i++) {
            char ch = s.charAt(i);
            boolean uniform = true;
            for (int j = i; j < i + k; j++) {
                if (s.charAt(j) != ch) {
                    uniform = false;
                    break;
                }
            }
            if (!uniform) continue;
            if (i > 0 && s.charAt(i - 1) == ch) continue;
            if (i + k < n && s.charAt(i + k) == ch) continue;
            return true;
        }
        return false;
    }
}
