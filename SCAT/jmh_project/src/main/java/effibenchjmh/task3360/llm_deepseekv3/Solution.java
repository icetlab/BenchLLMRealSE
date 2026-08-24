package effibenchjmh.task3360.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int minimumDeletions(String word, int k) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        List<Integer> l = new ArrayList<>(map.values());
        Collections.sort(l);
        int n = l.size();
        int min_evicted = Integer.MAX_VALUE;
        
        // Try each frequency as the minimum allowed frequency
        for (int i = 0; i < n; i++) {
            int minFreq = l.get(i);
            int maxFreq = minFreq + k;
            int deletions = 0;
            
            // For each character, decide deletions needed
            for (int j = 0; j < n; j++) {
                if (l.get(j) < minFreq) {
                    deletions += l.get(j);
                } else if (l.get(j) > maxFreq) {
                    deletions += l.get(j) - maxFreq;
                }
            }
            min_evicted = Math.min(min_evicted, deletions);
        }
        
        return min_evicted == Integer.MAX_VALUE ? 0 : min_evicted;
    }
}
