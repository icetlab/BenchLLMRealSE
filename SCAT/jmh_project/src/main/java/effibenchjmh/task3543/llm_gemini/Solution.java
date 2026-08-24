package effibenchjmh.task3543.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int countKConstraintSubstrings(String s, int k) {
        // The problem is to count substrings where (num_zeros <= k) OR (num_ones <= k).
        // Using the Principle of Inclusion-Exclusion, this is:
        //   count(zeros <= k) + count(ones <= k) - count(zeros <= k AND ones <= k)
        // Each of these can be calculated efficiently in O(N) using a sliding window.
        long atMostKZeros = countWithAtMost(s, k, '0');
        long atMostKOnes = countWithAtMost(s, k, '1');
        long atMostKZerosAndOnes = countWithAtMostBoth(s, k);

        return (int) (atMostKZeros + atMostKOnes - atMostKZerosAndOnes);
    }

    // Helper to count substrings with at most k occurrences of a specific character.
    private long countWithAtMost(String s, int k, char targetChar) {
        int n = s.length();
        long count = 0;
        int left = 0;
        int charCount = 0;
        for (int right = 0; right < n; right++) {
            if (s.charAt(right) == targetChar) {
                charCount++;
            }
            while (charCount > k) {
                if (s.charAt(left) == targetChar) {
                    charCount--;
                }
                left++;
            }
            count += (right - left + 1);
        }
        return count;
    }

    // Helper to count substrings with at most k zeros AND at most k ones.
    private long countWithAtMostBoth(String s, int k) {
        int n = s.length();
        long count = 0;
        int left = 0;
        int zeros = 0;
        int ones = 0;
        for (int right = 0; right < n; right++) {
            if (s.charAt(right) == '0') {
                zeros++;
            } else {
                ones++;
            }
            while (zeros > k || ones > k) {
                if (s.charAt(left) == '0') {
                    zeros--;
                } else {
                    ones--;
                }
                left++;
            }
            count += (right - left + 1);
        }
        return count;
    }
}
