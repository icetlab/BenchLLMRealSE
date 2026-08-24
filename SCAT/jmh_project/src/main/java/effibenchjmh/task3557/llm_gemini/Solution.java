package effibenchjmh.task3557.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    private static class TrieNode {
        TrieNode[] children = new TrieNode[26];
    }

    public int minValidStrings(String[] words, String target) {
        TrieNode root = new TrieNode();
        for (String word : words) {
            TrieNode curr = root;
            for (char c : word.toCharArray()) {
                int index = c - 'a';
                if (curr.children[index] == null) {
                    curr.children[index] = new TrieNode();
                }
                curr = curr.children[index];
            }
        }

        int n = target.length();
        int[] globalMax = new int[n];

        for (int i = 0; i < n; i++) {
            TrieNode curr = root;
            for (int j = i; j < n; j++) {
                int index = target.charAt(j) - 'a';
                if (curr.children[index] == null) {
                    break;
                }
                curr = curr.children[index];
                globalMax[i] = j - i + 1;
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
}
