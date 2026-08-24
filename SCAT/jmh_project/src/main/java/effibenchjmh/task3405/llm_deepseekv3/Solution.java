package effibenchjmh.task3405.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int numberOfSpecialChars(String word) {
        int count = 0;
        boolean[] seen = new boolean[128];
        boolean[] counted = new boolean[128];

        for (char c : word.toCharArray()) {
            if (seen[Character.toUpperCase(c)]) {
                if(counted[c]){
                    count--;
                    counted[Character.toUpperCase(c)] = false;
                    counted[Character.toLowerCase(c)] = false;
                }else
                continue;
            }
            if (Character.toUpperCase(c) == c && seen[Character.toLowerCase(c)]) {
                counted[c] = true;
                counted[Character.toLowerCase(c)] = true;
                count++;
            }

            seen[c] = true;
        }

        return count;
    }
}

