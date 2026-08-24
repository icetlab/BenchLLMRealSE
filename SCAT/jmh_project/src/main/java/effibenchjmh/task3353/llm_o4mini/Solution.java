package effibenchjmh.task3353.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public boolean isSubstringPresent(String s) {
    if (s.length() < 2) return false;
    HashSet<String> seen = new HashSet<>();
    for (int i = 1; i < s.length(); i++) {
        char a = s.charAt(i - 1), b = s.charAt(i);
        // immediate palindrome of length 2
        if (a == b) return true;
        // check if we've seen the reversed pair before
        String rev = "" + b + a;
        if (seen.contains(rev)) return true;
        // record the forward pair
        seen.add("" + a + b);
    }
    return false;
}
}
