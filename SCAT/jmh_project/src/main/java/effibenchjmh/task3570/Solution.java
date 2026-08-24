package effibenchjmh.task3570;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int countOfSubstrings(String word, int k) {
        HashSet<Character> vowels = new HashSet<>();
        vowels.add('a');
        vowels.add('e');
        vowels.add('i');
        vowels.add('o');
        vowels.add('u');

        HashMap<Character, Integer> vowelCount = new HashMap<>();
        int consonantCount = 0, l = 0, n = word.length(), ans = 0;

        for (int r = 0; r < n; r++) {
            if (vowels.contains(word.charAt(r))) {
                vowelCount.put(word.charAt(r), vowelCount.getOrDefault(word.charAt(r), 0) + 1);
            } else {
                consonantCount++;
            }

            while (consonantCount > k) {
                if (vowelCount.containsKey(word.charAt(l))) {
                    vowelCount.put(word.charAt(l), vowelCount.get(word.charAt(l)) - 1);
                    if (vowelCount.get(word.charAt(l)) == 0) {
                        vowelCount.remove(word.charAt(l));
                    }
                } else {
                    consonantCount--;
                }
                l++;
            }

            if (vowelCount.size() == 5 && consonantCount == k) {
                ans++;
                int lf = l;
                HashMap<Character, Integer> vowelCountCopy = new HashMap<>(vowelCount);
                int consonantCountCopy = consonantCount;

                while (vowelCountCopy.size() == 5) {
                    if (vowelCountCopy.containsKey(word.charAt(lf))) {
                        vowelCountCopy.put(word.charAt(lf), vowelCountCopy.get(word.charAt(lf)) - 1);
                        if (vowelCountCopy.get(word.charAt(lf)) == 0) {
                            vowelCountCopy.remove(word.charAt(lf));
                        }
                    } else {
                        consonantCountCopy--;
                    }
                    lf++;
                    if (consonantCountCopy == k && vowelCountCopy.size() == 5) ans++;
                    if (consonantCountCopy < k) break;
                }
            }
        }

        return ans;
    }
}
