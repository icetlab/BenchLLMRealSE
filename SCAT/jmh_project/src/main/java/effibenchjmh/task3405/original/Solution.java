package effibenchjmh.task3405.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int numberOfSpecialChars(String word) {
        int count = 0;
        Set<Character> set = new HashSet<>();
        Set<Character> alreadyCounted = new HashSet<>();

        for (char c : word.toCharArray()) {
            if (set.contains(Character.toUpperCase(c))) {
                if(alreadyCounted.contains(c)){
                    count--;
                    alreadyCounted.remove(Character.toUpperCase(c));
                    alreadyCounted.remove(Character.toLowerCase(c));
                }else
                continue;
            }
            if (Character.toUpperCase(c) == c && set.contains(Character.toLowerCase(c))) {
                alreadyCounted.add(c);
                alreadyCounted.add(Character.toLowerCase(c));
                count++;
            }

            set.add(c);
        }

        return count;
    }
}

