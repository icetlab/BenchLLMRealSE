package effibenchjmh.task3576.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        java.util.List<Integer>[] adj = new java.util.ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new java.util.ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            adj[parent[i]].add(i);
        }

        int[] newParents = parent.clone();

        int[] closest = new int[26];
        java.util.Arrays.fill(closest, -1);

        dfs(0, adj, s, closest, newParents);

        java.util.List<Integer>[] modifiedAdj = new java.util.ArrayList[n];
        for (int i = 0; i < n; i++) {
            modifiedAdj[i] = new java.util.ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            if (newParents[i] != -1) {
                modifiedAdj[newParents[i]].add(i);
            }
        }

        int[] sizes = new int[n];
        findSize(0, modifiedAdj, sizes);
        return sizes;
    }

    int findSize(int node, java.util.List<Integer>[] adj, int[] sizes) {
        int ans = 1;

        for(int neigh : adj[node]) {
            ans += findSize(neigh, adj, sizes);
        }

        return sizes[node] = ans;
    }

    void dfs(int node, java.util.List<Integer>[] adj, String s, int[] closest, int[] newParents) {
        int val = s.charAt(node) - 'a';

        if (closest[val] != -1) {
            newParents[node] = closest[val];
        }

        int temp = closest[val];
        closest[val] = node;

        for (int neigh : adj[node]) {
            dfs(neigh, adj, s, closest, newParents);
        }

        closest[val] = temp;
    }
}
