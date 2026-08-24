package effibenchjmh.task3197;

import java.util.*;
import java.util.stream.*;

class Solution {
    TrieNode root = new TrieNode();    
    public int maximumStrongPairXor(int[] nums) {
        int res = 0;
        for (int num : nums) {
            insert(num);
        }
        for (int num : nums) {
            int xor = find(num, num * 2);
            res = Math.max(res, num ^ xor);
        }
        return res;
    }
    
    public void insert(int num) {
        TrieNode node = root;
        for (int i = 20; i >= 0; i--) {
            int idx = (num >> i) & 1;
            if (node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
            node.min = Math.min(node.min, num);
            node.max = Math.max(node.max, num);
        }
    }
    
    public int find(int num, int range) {
        TrieNode node = root;
        int xor = 0;
        for (int i = 20; i >= 0; i--) {
            int a = (num >> i) & 1;
            int b = 1 - a;
            if (node.children[b] != null && node.children[b].min <= range && node.children[b].max > num) {
                xor = xor | b << i;
                node = node.children[b];
            } else {
                xor = xor | a << i;
                node = node.children[a];
            }
        }
        return xor;
    }
}

class TrieNode {
    TrieNode[] children = new TrieNode[2];
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
}
