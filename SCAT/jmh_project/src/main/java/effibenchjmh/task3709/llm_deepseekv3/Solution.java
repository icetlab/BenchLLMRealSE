package effibenchjmh.task3709.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public boolean hasSpecialSubstring(String s, int k) {
        int count = 1;
        for(int i=1; i<s.length(); i++) {
            if(s.charAt(i-1) == s.charAt(i)) {
                count++;
            } else {
                if(count == k) {
                    return true;
                }
                count = 1;
            }
        }
        if(count == k) {
            return true;
        }
        return false;
    }
}
