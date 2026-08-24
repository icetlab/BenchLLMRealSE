package effibenchjmh.task3363.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    private final ConcurrentSkipListMap<Long, Set<Integer>> freq2IdsMap = new ConcurrentSkipListMap<>();
    private final Map<Integer, Long> id2FreqMap = new HashMap<>();

    public long[] mostFrequentIDs(int[] nums, int[] freq) {
//        PriorityQueue<Pair<Long, Integer>> pq = new PriorityQueue<>((a, b) -> (int) (b.getKey() - a.getKey()));
        long[] ans = new long[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int id = nums[i];
            Long oldFreq = id2FreqMap.getOrDefault(id, 0L);
            Long newFreq = oldFreq + freq[i];
            id2FreqMap.put(id, newFreq);
            updateFreq2IdsMap(id, oldFreq, newFreq);
            ans[i] = freq2IdsMap.size() == 0 || (freq2IdsMap.get(freq2IdsMap.lastKey()).size() == 0) ? 0L : freq2IdsMap.lastKey();
        }
        return ans;
    }

    private void updateFreq2IdsMap(int id, Long oldFreq, Long newFreq) {
        if (freq2IdsMap.containsKey(oldFreq)) {
            Set<Integer> idSet = freq2IdsMap.get(oldFreq);
            idSet.remove(id);
            if (idSet.size() == 0) {
                freq2IdsMap.remove(oldFreq);
            }
            if (newFreq == 0) return;
        }

        freq2IdsMap.computeIfPresent(newFreq, (k, v) -> {
            v.add(id);
            return v;
        });
        freq2IdsMap.computeIfAbsent(newFreq, v -> {
            Set<Integer> set = new HashSet<>();
            set.add(id);
            return set;
        });

//        if (!freq2IdsMap.containsKey(newFreq)) {
//            Set<Integer> set = new HashSet<>();
//            set.add(id);
//            freq2IdsMap.put(newFreq, set);
//        } else {
//            freq2IdsMap.get(newFreq).add(id);
//        }
    }
   
}
