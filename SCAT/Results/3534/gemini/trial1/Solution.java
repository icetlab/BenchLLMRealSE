import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public int countPairs(int[] nums) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int num : nums) {
            counts.put(num, counts.getOrDefault(num, 0) + 1);
        }

        Map<String, List<Integer>> anagramGroups = new HashMap<>();
        for (int num : counts.keySet()) {
            char[] chars = Integer.toString(num).toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            anagramGroups.computeIfAbsent(key, k -> new ArrayList<>()).add(num);
        }

        long totalPairs = 0;

        // Part 1: Pairs within the same anagram group (identical and swappable anagrams)
        for (List<Integer> group : anagramGroups.values()) {
            for (int i = 0; i < group.size(); i++) {
                int num1 = group.get(i);
                int c1 = counts.get(num1);
                // Identical pairs
                totalPairs += (long) c1 * (c1 - 1) / 2;
                // Anagram pairs that are one swap away
                for (int j = i + 1; j < group.size(); j++) {
                    int num2 = group.get(j);
                    if (isSingleSwap(Integer.toString(num1), Integer.toString(num2))) {
                        totalPairs += (long) c1 * counts.get(num2);
                    }
                }
            }
        }

        // Part 2: Non-anagram pairs (e.g., 10 and 1)
        // These pairs arise when a swap creates a leading zero, changing the number of digits.
        for (int num : counts.keySet()) {
            String s = Integer.toString(num);
            // Must have a non-leading zero to form a smaller number
            if (s.length() <= 1 || s.indexOf('0', 1) == -1) continue;
            
            for (int i = 1; i < s.length(); i++) {
                if (s.charAt(i) == '0') {
                    char[] arr = s.toCharArray();
                    swap(arr, 0, i);
                    int partner = Integer.parseInt(new String(arr));
                    if (counts.containsKey(partner)) {
                        // This pair is guaranteed to be non-anagrammatic because `num` has a '0'
                        // that `partner` doesn't have in the same significance (it becomes a leading zero).
                        // This results in `partner` having fewer digits than `num`.
                        // We only iterate from the 'parent' (`num`) to the 'child' (`partner`),
                        // which ensures each such pair is counted only once.
                        totalPairs += (long) counts.get(num) * counts.get(partner);
                    }
                }
            }
        }
        
        return (int) totalPairs;
    }

    private boolean isSingleSwap(String s1, String s2) {
        // Assumes s1 and s2 are anagrams of the same length.
        int diff = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                diff++;
            }
        }
        return diff == 2;
    }

    private void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}