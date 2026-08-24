package effibenchjmh.task3353.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public boolean isSubstringPresent(String s) {
        if (s.length() < 2) return false;
        
        HashSet<String> common = new HashSet<>();
        for (int h = 0; h < s.length() - 1; h++) {
            common.add(s.substring(h, h + 2));
        }
        
        StringBuilder sb = new StringBuilder(s);
        String reversed = sb.reverse().toString();
        
        for (int i = 0; i < reversed.length() - 1; i++) {
            if (common.contains(reversed.substring(i, i + 2))) {
                return true;
            }
        }
        return false;
    }
}
