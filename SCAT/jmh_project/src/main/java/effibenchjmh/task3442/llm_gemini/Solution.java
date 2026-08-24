package effibenchjmh.task3442.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

import java.util.Arrays;
import java.util.BitSet;

class Solution {
    public int maxTotalReward(int[] arr) {
        Arrays.sort(arr);

        // BitSet to store all achievable reward sums.
        // Max possible reward is < 4000 (e.g., 1999 + 2000 = 3999).
        BitSet achievableSums = new BitSet(4000);
        achievableSums.set(0);

        int lastVal = -1;
        for (int val : arr) {
            // Process each unique value once
            if (val == lastVal) {
                continue;
            }
            lastVal = val;

            // We can add 'val' to any existing sum 's' as long as s < val.
            // Get all achievable sums smaller than the current value.
            BitSet sumsLessThanVal = achievableSums.get(0, val);
            
            // For each such sum 's', we can now achieve 's + val'.
            // This is equivalent to (sumsLessThanVal << val), but BitSet lacks a
            // direct shift operation, so we iterate.
            for (int s = sumsLessThanVal.nextSetBit(0); s >= 0; s = sumsLessThanVal.nextSetBit(s + 1)) {
                achievableSums.set(s + val);
            }
        }

        // The result is the largest achievable sum.
        return achievableSums.previousSetBit(3999);
    }
}
