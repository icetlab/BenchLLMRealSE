import java.util.HashMap;
import java.util.Map;

class Solution {
    public int numberOfPairs(int[] nums1, int[] nums2, int k) {
        Map<Integer, Integer> divisorCount = new HashMap<>();
        for (int num : nums1) {
            if (num % k != 0) continue;
            int value = num / k;
            for (int d = 1; d * d <= value; d++) {
                if (value % d == 0) {
                    divisorCount.put(d, divisorCount.getOrDefault(d, 0) + 1);
                    int other = value / d;
                    if (other != d) {
                        divisorCount.put(other, divisorCount.getOrDefault(other, 0) + 1);
                    }
                }
            }
        }
        int count = 0;
        for (int b : nums2) {
            count += divisorCount.getOrDefault(b, 0);
        }
        return count;
    }
}