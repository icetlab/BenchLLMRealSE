package effibenchjmh.task3384.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int minimumOperationsToMakeKPeriodic(String word, int k) {
        
        java.util.Map<String, Integer> freq = new java.util.HashMap<>();
        int maxFreq = 0;
        for (int i = 0; i + k <= word.length(); i += k) {
            String part = word.substring(i, i + k);
            int count = freq.merge(part, 1, Integer::sum);
            if (count > maxFreq) {
                maxFreq = count;
            }
        }
        return word.length() / k - Math.max(maxFreq, 1);
    }


}
