package effibenchjmh.task3353.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

import java.util.HashSet;

class Solution {
    public boolean isSubstringPresent(String s) {
        if (s.length() < 2) {
            return false;
        }
        StringBuilder reversed = new StringBuilder(s).reverse();
        String rev = reversed.toString();
        int n = s.length();
        HashSet<String> substrings = new HashSet<>();
        for (int i = 0; i < n - 1; i++) {
            substrings.add(s.substring(i, i + 2));
        }
        for (int i = 0; i < n - 1; i++) {
            if (substrings.contains(rev.substring(i, i + 2))) {
                return true;
            }
        }
        return false;
    }
}
