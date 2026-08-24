import java.util.*;

class Solution {
    public int beautifulSubstrings(String s, int k) {
        int n = s.length();
        // Precompute vowel classification for all ASCII chars
        boolean[] isVowel = new boolean[256];
        String vowels = "aeiou";
        for (int i = 0; i < vowels.length(); i++) {
            isVowel[vowels.charAt(i)] = true;
        }

        // Find smallest L such that L*L % k == 0
        int L = 1;
        while ((L * L) % k != 0) {
            L++;
        }

        // Map from (diff, vowelMod) to count of prefixes seen
        Map<Long, Integer> map = new HashMap<>();
        map.put(0L, 1); // initial prefix (diff=0, vowelMod=0)

        int diff = 0;
        int vowelCount = 0;
        int total = 0;
        // multiplier to combine diff and vowelMod into a unique key
        int modBase = k + 1; // vowelMod is always < L <= k, so k+1 is safe

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (isVowel[c]) {
                diff++;
                vowelCount++;
            } else {
                diff--;
            }
            int vowelMod = vowelCount % L;
            long key = (long) diff * modBase + vowelMod;
            int prev = map.getOrDefault(key, 0);
            total += prev;
            map.put(key, prev + 1);
        }
        return total;
    }
}