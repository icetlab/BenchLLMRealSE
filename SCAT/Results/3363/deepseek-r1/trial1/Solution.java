class Solution {
    private final java.util.TreeMap<Long, Integer> freqCount = new java.util.TreeMap<>();
    private final java.util.Map<Integer, Long> id2FreqMap = new java.util.HashMap<>();

    public long[] mostFrequentIDs(int[] nums, int[] freq) {
        long[] ans = new long[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int id = nums[i];
            long oldFreq = id2FreqMap.getOrDefault(id, 0L);
            long newFreq = oldFreq + freq[i];

            if (oldFreq != 0) {
                Integer count = freqCount.get(oldFreq);
                if (count != null) {
                    if (count == 1) {
                        freqCount.remove(oldFreq);
                    } else {
                        freqCount.put(oldFreq, count - 1);
                    }
                }
            }

            if (newFreq != 0) {
                id2FreqMap.put(id, newFreq);
                Integer count = freqCount.get(newFreq);
                freqCount.put(newFreq, count == null ? 1 : count + 1);
            } else {
                id2FreqMap.remove(id);
            }

            ans[i] = freqCount.isEmpty() ? 0L : freqCount.lastKey();
        }
        return ans;
    }
   
}