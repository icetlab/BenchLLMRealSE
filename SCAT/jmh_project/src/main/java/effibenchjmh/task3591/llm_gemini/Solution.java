package effibenchjmh.task3591.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public long shiftDistance(String s, String t, int[] nextCost, int[] previousCost) {
        long[] prefixNextCost = new long[27];
        long[] prefixPreviousCost = new long[27];

        for (int i = 0; i < 26; i++) {
            prefixNextCost[i + 1] = prefixNextCost[i] + nextCost[i];
            prefixPreviousCost[i + 1] = prefixPreviousCost[i] + previousCost[i];
        }

        long mainSum = 0;

        for (int i = 0; i < s.length(); i++) {
            char s_char = s.charAt(i);
            char t_char = t.charAt(i);

            if (s_char != t_char) {
                int s_idx = s_char - 'a';
                int t_idx = t_char - 'a';

                // Straight path cost from s_char to t_char
                long straSum;
                if (s_idx < t_idx) {
                    straSum = prefixNextCost[t_idx] - prefixNextCost[s_idx];
                } else {
                    straSum = (prefixNextCost[26] - prefixNextCost[s_idx]) + prefixNextCost[t_idx];
                }

                // Reverse path cost from s_char to t_char
                // The original logic was equivalent to summing previousCost from ((t_char-'a')+1)%26 to (s_char-'a') inclusive.
                int start_r = ((t_char - 'a') + 1) % 26;
                int end_r = s_char - 'a';
                long revSum;
                if (start_r <= end_r) {
                    revSum = prefixPreviousCost[end_r + 1] - prefixPreviousCost[start_r];
                } else {
                    revSum = (prefixPreviousCost[26] - prefixPreviousCost[start_r]) + prefixPreviousCost[end_r + 1];
                }

                mainSum += Math.min(revSum, straSum);
            }
        }
        return mainSum;
    }
}
