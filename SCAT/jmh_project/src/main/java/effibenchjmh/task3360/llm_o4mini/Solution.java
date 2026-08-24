package effibenchjmh.task3360.llm_o4mini;

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
        int[] prefix = new int[n + 1];
        for (int i = 0; i < n; i++) prefix[i + 1] = prefix[i] + l.get(i);
        int left = 0;
        int min_evicted = Integer.MAX_VALUE;
        for (int right = 0; right < l.size(); right ++){
            if (l.get(right) - l.get(left) <= k && right + 1 < l.size() && l.get(right + 1) - l.get(left) <= k) continue;
            while (left < right && l.get(right) - l.get(left) > k){
                //I WILL LOOK BACK AND LOOK AHEAD:
                //MAKE THEM EQUAL 
                int sum1 = prefix[left];
                int sum2 = prefix[n] - prefix[right];
                min_evicted = Math.min(min_evicted, sum1 + (sum2 - Math.max(l.size() - right, 0) * (k + l.get(left))));
                left ++;
            }
            if (l.get(right) - l.get(left) <= k && right + 1 < l.size() && l.get(right + 1) - l.get(left) <= k) continue;
            if (l.get(right) - l.get(left) <= k){
                //DO NOTHING
                int sum1 = prefix[left];
                int sum2 = prefix[n] - prefix[right + 1];
                min_evicted = Math.min(min_evicted, sum1 + (sum2 - Math.max(l.size() - right - 1, 0) * (k + l.get(left))));
            }
        }

        return min_evicted == Integer.MAX_VALUE ? 0 : min_evicted;

    }
}
