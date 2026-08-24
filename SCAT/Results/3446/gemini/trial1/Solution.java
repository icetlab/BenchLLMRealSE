import java.util.HashMap;
import java.util.Map;
class Solution {
    public int numberOfPairs(int[] nums1, int[] nums2, int k) {

        int maxNum1 = 0;
        for (int num : nums1) {
            if (num > maxNum1) {
                maxNum1 = num;
            }
        }

        if (maxNum1 == 0) {
            return 0;
        }

        int[] freq1 = new int[maxNum1 + 1];
        for (int num : nums1) {
            freq1[num]++;
        }

        Map<Integer, Integer> freq2_k = new HashMap<>();
        for (int num2 : nums2) {
            long target = (long) num2 * k;
            if (target > maxNum1) {
                continue;
            }
            int t = (int) target;
            freq2_k.put(t, freq2_k.getOrDefault(t, 0) + 1);
        }

        long count = 0;
        for (Map.Entry<Integer, Integer> entry : freq2_k.entrySet()) {
            int t = entry.getKey();
            int numOccurrences = entry.getValue();
            for (int multiple = t; multiple <= maxNum1; multiple += t) {
                count += (long) numOccurrences * freq1[multiple];
            }
        }

        return (int) count;
    }
}