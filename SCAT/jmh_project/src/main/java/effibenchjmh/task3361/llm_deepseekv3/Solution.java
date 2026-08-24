package effibenchjmh.task3361.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public String findLatestTime(String s) {
        char[] chars = s.toCharArray();
        
        // Process hour tens digit (position 0)
        if (chars[0] == '?') {
            if (chars[1] == '?' || chars[1] <= '1') {
                chars[0] = '1';
            } else {
                chars[0] = '0';
            }
        }
        
        // Process hour ones digit (position 1)
        if (chars[1] == '?') {
            if (chars[0] == '1') {
                chars[1] = '1';
            } else {
                chars[1] = '9';
            }
        }
        
        // Process minute tens digit (position 3)
        if (chars[3] == '?') {
            chars[3] = '5';
        }
        
        // Process minute ones digit (position 4)
        if (chars[4] == '?') {
            chars[4] = '9';
        }
        
        return new String(chars);
    }
}
