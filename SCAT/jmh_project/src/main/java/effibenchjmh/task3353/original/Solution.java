package effibenchjmh.task3353.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    public boolean isSubstringPresent(String s) {
    String k = "";
    HashSet<String> common = new HashSet<>();
    for (int i = s.length()-1; i >= 0; i--) {
        k += s.charAt(i);
    }

    for (int h = 0; h < (s.length()-1); h += 1) {
        common.add(s.substring(h,h+2));
    }

    for (int i = 0; i < s.length() - 1; i++) {
        if (common.contains(k.substring(i,i+2))) {
            return true;
        }
    }
    return false;
}
}
