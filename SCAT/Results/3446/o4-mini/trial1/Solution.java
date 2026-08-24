import java.util.HashMap;
import java.util.Map;
class Solution {
    public int numberOfPairs(int[] nums1, int[] nums2, int k) {

        int count=0;

       Map<Integer,Integer> freq = new HashMap<>();

       for (int num : nums2) {
           freq.put(num, freq.getOrDefault(num, 0) + 1);
       }

       for (int x : nums1) {
           if (x % k != 0) continue;
           int y = x / k;
           int limit = (int) Math.sqrt(y);
           for (int d = 1; d <= limit; d++) {
               if (y % d == 0) {
                   if (freq.containsKey(d)) {
                       count += freq.get(d);
                   }
                   int other = y / d;
                   if (other != d && freq.containsKey(other)) {
                       count += freq.get(other);
                   }
               }
           }
       }
        return count;
    }
}