package effibenchjmh.task3245.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public List<Integer> beautifulIndices(String s, String a, String b, int k) {
        
        List<Integer> aArray  =  construct(s,a);
        List<Integer> bArray =  construct(s,b);

        List<Integer> answer = new ArrayList<>();
        int j = 0;
        for (int i = 0; i < aArray.size(); i++) {
            int aIdx = aArray.get(i);
            // Move j forward to find the first b index >= aIdx - k
            while (j < bArray.size() && bArray.get(j) < aIdx - k) {
                j++;
            }
            // Check if this b index is within range
            if (j < bArray.size() && Math.abs(aIdx - bArray.get(j)) <= k) {
                answer.add(aIdx);
            }
        }
        return answer;
    }

   private List<Integer> construct(String s, String pattern) {
    String combined = pattern + "#" + s;
    int[] array = new int[combined.length()];
    
    int i = 1, len = 0;

    while (i < combined.length()) {
        if (combined.charAt(i) == combined.charAt(len)) {
            len++;
            array[i] = len;
            i++;
        } else {
            if (len != 0) {
                len = array[len - 1];
            } else {
                i++;
            }
        }
    }

    List<Integer> list = new ArrayList<>();
    for (i = pattern.length() + 1; i < combined.length(); i++) {
        if (array[i] == pattern.length()) {
            list.add(i - 2 * pattern.length());  // Corrected index calculation
        }
    }

    return list;
}

}
