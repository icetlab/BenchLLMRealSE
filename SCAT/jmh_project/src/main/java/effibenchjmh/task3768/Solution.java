package effibenchjmh.task3768;

import java.util.*;
import java.util.stream.*;

class Solution {
    public boolean hasSameDigits(String s) {
        int[] current = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            current[i] = s.charAt(i) - '0';
        }
        while (current.length > 2) {
            int[] next = new int[current.length - 1];
            for (int i = 0; i < current.length - 1; i++) {
                next[i] = (current[i] + current[i + 1]) % 10;
            }
            current = next;
        }
        return current[0] == current[1];
    }
}
