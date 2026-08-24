package effibenchjmh.task3753;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int maxDifference(String s) {
        int[] freq = new int[26];
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }
        java.util.List<Integer> even = new java.util.ArrayList<>();
        java.util.List<Integer> odd = new java.util.ArrayList<>();
        for (int count : freq) {
            if (count > 0) {
                if (count % 2 == 0) {
                    even.add(count);
                } else {
                    odd.add(count);
                }
            }
        }
        int maxOdd = java.util.Collections.max(odd);
        int minEven = java.util.Collections.min(even);
        return maxOdd - minEven;
    }
}
