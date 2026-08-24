package effibenchjmh.task3384.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int minimumOperationsToMakeKPeriodic(String word, int k) {
        
                int m = word.length() / k;
        if (m <= 1) return 0;
        Map<String, Integer> mp = new HashMap<>(m);
        int maxCount = 0;
        for (int i = 0; i < m; i++) {
            int start = i * k;
            String s = word.substring(start, start + k);
            int cnt = mp.getOrDefault(s, 0) + 1;
            mp.put(s, cnt);
            if (cnt > maxCount) maxCount = cnt;
        }
        return m - maxCount;
    }


}
