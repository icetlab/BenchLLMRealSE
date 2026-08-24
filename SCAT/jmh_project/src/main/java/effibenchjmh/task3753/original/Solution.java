package effibenchjmh.task3753.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int maxDifference(String s) {
        HashMap<Character, Integer> map = new HashMap<>();

        // Count frequencies
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }

        int max = Integer.MIN_VALUE;

        // Compare odd and even frequencies
        for (Map.Entry<Character, Integer> entry1 : map.entrySet()) {
            int freq1 = entry1.getValue();
            for (Map.Entry<Character, Integer> entry2 : map.entrySet()) {
                int freq2 = entry2.getValue();

                // freq1 is odd, freq2 is even → calculate (odd - even)
                if (freq1 % 2 != 0 && freq2 % 2 == 0) {
                    max = Math.max(max, freq1 - freq2);
                }
            }
        }

        return max == Integer.MIN_VALUE ? 0 : max;
    }
}

