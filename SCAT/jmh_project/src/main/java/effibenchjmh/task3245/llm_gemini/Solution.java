package effibenchjmh.task3245.llm_gemini;

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
        int bIndex = 0;
        for (int aIndex : aArray) {
            // Advance the bIndex to the start of the window [aIndex - k, aIndex + k]
            while (bIndex < bArray.size() && bArray.get(bIndex) < aIndex - k) {
                bIndex++;
            }
            // Check if the current bIndex is within the window
            if (bIndex < bArray.size() && bArray.get(bIndex) <= aIndex + k) {
                answer.add(aIndex);
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
