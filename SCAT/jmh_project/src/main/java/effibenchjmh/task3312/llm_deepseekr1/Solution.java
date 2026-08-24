package effibenchjmh.task3312.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int countKeyChanges(String s) {
        int len = s.length();
        if (len == 0) {
            return 0;
        }
        int count = 0;
        int prev = Character.toLowerCase(s.charAt(0));
        for (int i = 1; i < len; i++) {
            int cur = Character.toLowerCase(s.charAt(i));
            if (prev != cur) {
                count++;
                prev = cur;
            }
        }
        return count;
    }
}
