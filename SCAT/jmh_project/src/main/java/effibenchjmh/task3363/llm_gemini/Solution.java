package effibenchjmh.task3363.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    private final java.util.TreeMap<Long, Set<Integer>> freq2IdsMap = new java.util.TreeMap<>();
    private final Map<Integer, Long> id2FreqMap = new HashMap<>();

    public long[] mostFrequentIDs(int[] nums, int[] freq) {
        long[] ans = new long[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int id = nums[i];
            Long oldFreq = id2FreqMap.getOrDefault(id, 0L);
            Long newFreq = oldFreq + freq[i];
            id2FreqMap.put(id, newFreq);
            updateFreq2IdsMap(id, oldFreq, newFreq);
            ans[i] = freq2IdsMap.isEmpty() ? 0L : freq2IdsMap.lastKey();
        }
        return ans;
    }

    private void updateFreq2IdsMap(int id, Long oldFreq, Long newFreq) {
        if (oldFreq != 0) {
            Set<Integer> idSet = freq2IdsMap.get(oldFreq);
            idSet.remove(id);
            if (idSet.isEmpty()) {
                freq2IdsMap.remove(oldFreq);
            }
        }

        if (newFreq != 0) {
            freq2IdsMap.computeIfAbsent(newFreq, k -> new HashSet<>()).add(id);
        }
    }
   
}
