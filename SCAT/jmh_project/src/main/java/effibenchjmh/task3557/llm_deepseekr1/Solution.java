package effibenchjmh.task3557.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

import java.util.*;

class Solution {
    // We'll choose a random base between low and high.
    // Using a large range reduces the chance of collisions.
    final long BASE;
    
    public Solution() {
        // Define lower and upper bounds.
        long low = 100_000L;      
        long high = 10_000_000L;  
        Random rng = new Random(System.nanoTime());
        // Compute a random long in [low, high)
        long range = high - low;
        long candidate = low + Math.abs(rng.nextLong()) % range;
        // Make sure it is odd.
        if ((candidate & 1) == 0) { 
            candidate++;
        }
        BASE = candidate;
        // System.out.println("Using BASE = " + BASE);
    }
    
    public int minValidStrings(String[] words, String target) {
        int n = target.length();
        int[] globalMax = new int[n];  // globalMax[i] is the maximum valid segment length starting at i.
        Arrays.fill(globalMax, 0);

        // Precompute hash and power arrays for target.
        long[] targetHash = new long[n + 1]; // targetHash[i] = hash of target[0...i-1]
        long[] targetPow  = new long[n + 1]; // targetPow[i] = BASE^i in 64-bit arithmetic
        targetPow[0] = 1;
        for (int i = 0; i < n; i++) {
            // Map characters to positive numbers: (char - 'a' + 1)
            targetHash[i + 1] = targetHash[i] * BASE + (target.charAt(i) - 'a' + 1);
            targetPow[i + 1] = targetPow[i] * BASE;
        }
        
        // Store the hash of every word prefix by its length.
        // Prefixes longer than target.length() can never be used, so cap them at n.
        java.util.List<java.util.Set<Long>> prefixSets = new java.util.ArrayList<java.util.Set<Long>>(n + 1);
        for (int i = 0; i <= n; i++) {
            prefixSets.add(null);
        }
        boolean[] targetCharPresent = new boolean[65536];
        for (int i = 0; i < n; i++) {
            targetCharPresent[target.charAt(i)] = true;
        }

        for (String word : words) {
            if (!targetCharPresent[word.charAt(0)]) continue;
            long h = 0;
            int limit = Math.min(word.length(), n);
            for (int len = 1; len <= limit; len++) {
                h = h * BASE + (word.charAt(len - 1) - 'a' + 1);
                java.util.Set<Long> set = prefixSets.get(len);
                if (set == null) {
                    set = new java.util.HashSet<Long>();
                    prefixSets.set(len, set);
                }
                set.add(h);
            }
        }

        // For each start index, binary search the longest matching prefix length.
        // If length L matches, all shorter prefixes of the same word match too.
        for (int i = 0; i < n; i++) {
            int lo = 1, hi = n - i, best = 0;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                long targetSubHash = targetHash[i + mid] - targetHash[i] * targetPow[mid];
                java.util.Set<Long> set = prefixSets.get(mid);
                if (set != null && set.contains(targetSubHash)) {
                    best = mid;
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
            globalMax[i] = best;
        }
        
        // Greedy jump-game: from index i, you can jump to i + globalMax[i].
        int jumps = 0, currentEnd = 0, farthest = 0;
        for (int i = 0; i < n; i++) {
            if (i > farthest) return -1;  // unreachable.
            farthest = Math.max(farthest, i + globalMax[i]);
            if (i == currentEnd) {
                jumps++;
                currentEnd = farthest;
                if (currentEnd >= n) break;
            }
        }
        return farthest < n ? -1 : jumps;
    }
    
    // Helper function: Check if target substring [l, r) equals word prefix of length len.
    private boolean equalSubstr(long[] targetHash, long[] targetPow, int l, int r, long[] wordHash, int len) {
        long hashTarget = targetHash[r] - targetHash[l] * targetPow[r - l];
        long hashWord = wordHash[len];
        return hashTarget == hashWord;
    }
}
