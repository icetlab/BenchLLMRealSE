package effibenchjmh.task3768.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public boolean hasSameDigits(String s) {
        int n = s.length();
        int[] digits = new int[n];
        for (int i = 0; i < n; i++) {
            digits[i] = s.charAt(i) - '0';
        }
        
        while (digits.length > 2) {
            int[] next = new int[digits.length - 1];
            for (int i = 0; i < next.length; i++) {
                next[i] = (digits[i] + digits[i+1]) % 10;
            }
            digits = next;
        }
        
        return digits[0] == digits[1];
    }
}
