package effibenchjmh.task3576.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

import java.util.*;

class Solution {

    private int[] solve(int[] parent, String s) {
        int n = parent.length;

        int[] head = new int[n];
        int[] next = new int[n];
        int[] saved = new int[n];
        int[] res = new int[n];
        java.util.Arrays.fill(head, -1);

        int[] closest = new int[27];
        java.util.Arrays.fill(closest, -1);

        for (int i = 1; i < n; i++) {
            int p = parent[i];
            next[i] = head[p];
            head[p] = i;
        }

        int[] stack = new int[n];
        int top = 0;

        int root = 0;
        closest[s.charAt(root) - 'a'] = root;
        saved[root] = -1;
        res[root] = 1;
        stack[top++] = root;

        while (top > 0) {
            int u = stack[top - 1];
            int v = head[u];

            if (v != -1) {
                head[u] = next[v];

                int ch = s.charAt(v) - 'a';
                saved[v] = closest[ch];
                closest[ch] = v;
                res[v] = 1;
                stack[top++] = v;
            } else {
                top--;

                int p = (saved[u] != -1) ? saved[u] : parent[u];
                if (p != -1) {
                    res[p] += res[u];
                }

                closest[s.charAt(u) - 'a'] = saved[u];
            }
        }

        return res;
    }

    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        // adjacency is built in solve(parent, s)

       // System.out.println(adj2[1].toString());

        // dfs moved into solve(parent, s)
    
        int[] sizes = solve(parent, s);



        // findSize moved into solve(parent, s)

      //  System.out.println(adj2[1].toString());

        return sizes;
    }

    int findSize(int node, Set<Integer>[] adj, int[] sizes) {
        int ans = 1;

        for(int neigh : adj[node]) {
            ans += findSize(neigh, adj, sizes);
        }

        return sizes[node] = ans;
    }

    void dfs(int node, Set<Integer>[] adj, String s, Integer[] closest, int parent, Set<Integer>[] adj2) {
        int val = s.charAt(node) - 'a';

     //   System.out.println("Came to " + node);

        if (closest[val] != null) {
            int newParent = closest[val];
       //     System.out.println("Removing from " + parent + " adding to " + newParent);
            adj2[parent].remove(node);
            adj2[newParent].add(node);
        } 

        Integer temp = closest[val];

        closest[val] = node;

        //System.out.println("Set " + val + " for node " + node);

        for(int neigh : adj[node]) {
            dfs(neigh, adj, s, closest, node, adj2);
        }

        closest[val] = temp;
    }
}
