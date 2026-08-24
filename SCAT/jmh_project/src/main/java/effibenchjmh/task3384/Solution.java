package effibenchjmh.task3384;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int minimumOperationsToMakeKPeriodic(String word, int k) {
        int n = word.length();
        Map<String, Integer> freqMap = new HashMap<>();
        int maxFreq = 0;
        for(int i = 0; i < n; i += k) {
            String segment = word.substring(i, Math.min(i + k, n));
            freqMap.put(segment, freqMap.getOrDefault(segment, 0) + 1);
            maxFreq = Math.max(maxFreq, freqMap.get(segment));
        }
        int totalSegments = n / k;
        int minOperations = totalSegments - maxFreq;
        return minOperations;
    }
}
