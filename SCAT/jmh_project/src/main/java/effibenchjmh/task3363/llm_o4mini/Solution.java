package effibenchjmh.task3363.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    private final TreeMap<Long, Integer> freqCountMap = new TreeMap<>();
    private final Map<Integer, Long> id2FreqMap = new HashMap<>();
    private long maxFreq = 0;

    public long[] mostFrequentIDs(int[] nums, int[] freq) {
        long[] ans = new long[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int id = nums[i];
            long oldFreq = id2FreqMap.getOrDefault(id, 0L);
            long newFreq = oldFreq + freq[i];
            id2FreqMap.put(id, newFreq);
            // decrement count for old frequency
            if (oldFreq > 0) {
                int count = freqCountMap.get(oldFreq) - 1;
                if (count == 0) {
                    freqCountMap.remove(oldFreq);
                    if (oldFreq == maxFreq) {
                        maxFreq = freqCountMap.isEmpty() ? 0 : freqCountMap.lastKey();
                    }
                } else {
                    freqCountMap.put(oldFreq, count);
                }
            }
            // increment count for new frequency
            if (newFreq > 0) {
                freqCountMap.put(newFreq, freqCountMap.getOrDefault(newFreq, 0) + 1);
                if (newFreq > maxFreq) {
                    maxFreq = newFreq;
                }
            }
            ans[i] = maxFreq;
        }
        return ans;
    }


   
}
