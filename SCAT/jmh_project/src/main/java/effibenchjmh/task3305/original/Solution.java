package effibenchjmh.task3305.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    // Inner class representing a node in the trie
    private class TrieNode {
      // Map to store child nodes based on character pairs
      private HashMap<List<Character>, TrieNode> children = new HashMap<>();
      // Counting or "ginti" associated with the current path in the trie
      private int ginti;
     }

 // Method to count prefix-suffix pairs in an array of words
 public long countPrefixSuffixPairs(String[] words) {
  
        // Initializing the root of the trie
  TrieNode root = new TrieNode();
  // Variable to keep track of the count or "ginti" of prefix-suffix pairs
  long ginti = 0;

  // Iterating through each word in the array
  for (String word : words) {
   // Starting at the root for each word
   TrieNode node = root;

   // Iterating through each character in the word
   for (int i = 0; i < word.length(); i++) {
    // Computing or retrieve the child node for the character pair
    ginti += (node = node.children.computeIfAbsent(
      List.of(word.charAt(i), word.charAt(word.length() - i - 1)),
      t -> new TrieNode())).ginti;
   }

   // Incrementing the count or "ginti" for the current word's path in the trie
   node.ginti++;
  }

  // Returning the total count or "ginti" of prefix-suffix pairs
  return ginti;
 }
}
