package effibenchjmh.task3210.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int beautifulSubstrings(String s, int k) {
        int m = 1;
        int temp_k = k;
        for (int i = 2; i * i <= temp_k; i++) {
            if (temp_k % i == 0) {
                int count = 0;
                while (temp_k % i == 0) {
                    count++;
                    temp_k /= i;
                }
                int power = (count + 1) / 2;
                for (int j = 0; j < power; j++) {
                    m *= i;
                }
            }
        }
        if (temp_k > 1) {
            m *= temp_k;
        }

        int mod = 2 * m;
        int n = s.length();
        java.util.Map<Long, Integer> counts = new java.util.HashMap<>();

        int diff = 0;
        // Key is a combination of (diff, index % mod)
        long key = (long) (0 + n) * mod + (0 % mod);
        counts.put(key, 1); // For empty prefix at index 0

        int result = 0;
        boolean[] isVowel = new boolean[26];
        isVowel['a' - 'a'] = true;
        isVowel['e' - 'a'] = true;
        isVowel['i' - 'a'] = true;
        isVowel['o' - 'a'] = true;
        isVowel['u' - 'a'] = true;

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (isVowel[c - 'a']) {
                diff++;
            } else {
                diff--;
            }

            int p = i + 1;
            key = (long) (diff + n) * mod + (p % mod);

            result += counts.getOrDefault(key, 0);
            counts.put(key, counts.getOrDefault(key, 0) + 1);
        }

        return result;
        }   
}
