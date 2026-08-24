package effibenchjmh.task3753.llm_gemini;

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

        // Find the maximum odd frequency and minimum even frequency
        int maxOdd = Integer.MIN_VALUE;
        int minEven = Integer.MAX_VALUE;

        for (int freq : map.values()) {
            if (freq % 2 != 0) { // Odd frequency
                maxOdd = Math.max(maxOdd, freq);
            } else { // Even frequency
                minEven = Math.min(minEven, freq);
            }
        }

        // If no odd or no even frequencies were found, no valid difference exists.
        if (maxOdd == Integer.MIN_VALUE || minEven == Integer.MAX_VALUE) {
            return 0;
        }

        return maxOdd - minEven;
    }
}

