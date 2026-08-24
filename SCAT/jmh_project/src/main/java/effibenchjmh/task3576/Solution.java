package effibenchjmh.task3576;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 1; i < n; i++) {
            adj.get(parent[i]).add(i);
        }

        int[] newParent = Arrays.copyOf(parent, n);
        Map<Character, Integer> lastSeen = new HashMap<>();
        dfsAdjustParent(0, s, adj, lastSeen, newParent);

        List<List<Integer>> finalAdj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            finalAdj.add(new ArrayList<>());
        }
        for (int i = 1; i < n; i++) {
            finalAdj.get(newParent[i]).add(i);
        }

        int[] answer = new int[n];
        dfsCountSubtreeSizes(0, finalAdj, answer);
        
        return answer;
    }

    public void dfsAdjustParent(int node, String s, List<List<Integer>> adj, Map<Character, Integer> lastSeen, int[] newParent) {
        char c = s.charAt(node);
        Integer prev = lastSeen.get(c);
        if (prev != null && prev != newParent[node]) {
            newParent[node] = prev;
        }

        Integer originalParent = lastSeen.put(c, node);
        for (int child : adj.get(node)) {
            dfsAdjustParent(child, s, adj, lastSeen, newParent);
        }
        if (originalParent == null) {
            lastSeen.remove(c);
        } else {
            lastSeen.put(c, originalParent);
        }
    }

    public int dfsCountSubtreeSizes(int node, List<List<Integer>> adj, int[] answer) {
        int size = 1;
        for (int child : adj.get(node)) {
            size += dfsCountSubtreeSizes(child, adj, answer);
        }
        answer[node] = size;
        return size;
    }
}
