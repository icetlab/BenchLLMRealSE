import java.util.*;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        List<Integer>[] adj = new List[n];
        Set<Integer>[] adj2 = new Set[n];

        for(int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
            adj2[i] = new HashSet();
        }

        for(int i = 1; i < n; i++) {
            adj[parent[i]].add(i);
            adj2[parent[i]].add(i);
        }

       // System.out.println(adj2[1].toString());

        int[] closest = new int[26];
        java.util.Arrays.fill(closest, -1);
        dfs(0, adj, s, closest, -1, adj2);
    
        int[] sizes = new int[n];



        findSize(0, adj2, sizes);

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

    void dfs(int node, List<Integer>[] adj, String s, int[] closest, int parent, Set<Integer>[] adj2) {
        int val = s.charAt(node) - 'a';

     //   System.out.println("Came to " + node);

        if (closest[val] != -1) {
            int newParent = closest[val];
       //     System.out.println("Removing from " + parent + " adding to " + newParent);
            adj2[parent].remove(node);
            adj2[newParent].add(node);
        } 

        int temp = closest[val];

        closest[val] = node;

        //System.out.println("Set " + val + " for node " + node);

        for(int neigh : adj[node]) {
            dfs(neigh, adj, s, closest, node, adj2);
        }

        closest[val] = temp;
    }
}