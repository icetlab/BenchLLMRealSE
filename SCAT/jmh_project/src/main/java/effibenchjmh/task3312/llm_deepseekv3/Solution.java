package effibenchjmh.task3312.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int countKeyChanges(String s) {
        int count = 0;
        for (int i = 0; i < s.length() - 1; i++) {
            if (Character.toLowerCase(s.charAt(i)) != Character.toLowerCase(s.charAt(i + 1))) {
                count++;
            }
        }
        return count;
    }
}
