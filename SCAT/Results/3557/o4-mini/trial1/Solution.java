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
        
        // Precompute positions of each character in target for fast lookup.
        List<Integer>[] charIndices = new ArrayList[26];
        for (int i = 0; i < 26; i++) charIndices[i] = new ArrayList<>();
        for (int i = 0; i < n; i++) charIndices[target.charAt(i)-'a'].add(i);
        
        // Process each word.
        for (String word : words) {
            int m = word.length();
            // Skip if the first character of word is not in target.
            int c = word.charAt(0) - 'a';
            if (charIndices[c].isEmpty()) continue;
            
            // Precompute hash and power arrays for the word.
            long[] wordHash = new long[m + 1];
            long[] wordPow = new long[m + 1];
            wordPow[0] = 1;
            for (int i = 0; i < m; i++) {
                wordHash[i + 1] = wordHash[i] * BASE + (word.charAt(i) - 'a' + 1);
                wordPow[i + 1] = wordPow[i] * BASE;
            }
            
            // For each index in target where the first character matches word.charAt(0),
            // use binary search to find the longest prefix of word that matches the target substring.
            for (int i : charIndices[c]) {
                int hi = Math.min(m, n - i);
                int lo = 1, best = 0;
                while (lo <= hi) {
                    int mid = lo + (hi - lo) / 2;
                    if (equalSubstr(targetHash, targetPow, i, i + mid, wordHash, mid)) {
                        best = mid;
                        lo = mid + 1;
                    } else {
                        hi = mid - 1;
                    }
                }
                globalMax[i] = Math.max(globalMax[i], best);
            }
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