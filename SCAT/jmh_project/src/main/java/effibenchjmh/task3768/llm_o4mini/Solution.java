package effibenchjmh.task3768.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public boolean hasSameDigits(String s) {
        while (s.length() > 2) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length() - 1; i++) {
                int newNum = ((s.charAt(i) - '0') + (s.charAt(i + 1) - '0')) % 10;
                sb.append(newNum);
            }
            s = sb.toString();
        }
        return s.charAt(0) == s.charAt(1);
    }
}
