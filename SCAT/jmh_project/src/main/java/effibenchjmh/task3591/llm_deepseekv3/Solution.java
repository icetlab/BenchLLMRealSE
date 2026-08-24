package effibenchjmh.task3591.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public long shiftDistance(String s, String t, int[] nextCost, int[] previousCost) {
        // Precompute all pairwise costs for both directions
        long[][] straightCost = new long[26][26];
        long[][] reverseCost = new long[26][26];
        
        for (int start = 0; start < 26; start++) {
            for (int end = 0; end < 26; end++) {
                if (start == end) {
                    straightCost[start][end] = 0;
                    reverseCost[start][end] = 0;
                } else {
                    // Straight: start -> end clockwise
                    long sum = 0;
                    int cur = start;
                    while (true) {
                        sum += nextCost[cur];
                        cur = (cur + 1) % 26;
                        if (cur == end) break;
                    }
                    straightCost[start][end] = sum;
                    
                    // Reverse: start -> end counter-clockwise
                    sum = 0;
                    cur = (start + 1) % 26;
                    while (true) {
                        sum += previousCost[cur];
                        if (cur == end) break;
                        cur = (cur + 1) % 26;
                    }
                    reverseCost[start][end] = sum;
                }
            }
        }
        
        long mainSum = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != t.charAt(i)) {
                int start = s.charAt(i) - 'a';
                int end = t.charAt(i) - 'a';
                mainSum += Math.min(straightCost[start][end], reverseCost[end][start]);
            }
        }
        return mainSum;
    }
}
