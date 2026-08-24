package effibenchjmh.task3684;

import java.util.*;
import java.util.stream.*;

class Solution {
    public boolean hasMatch(String s, String p) {
        int starIndex = p.indexOf('*');
        String prefix = p.substring(0, starIndex);
        String suffix = p.substring(starIndex + 1);
        int n = s.length();
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int len = j - i + 1;
                if (len < prefix.length() + suffix.length()) continue;
                String candidate = s.substring(i, j + 1);
                if (candidate.startsWith(prefix) && candidate.endsWith(suffix))
                    return true;
            }
        }
        return false;
    }
}
