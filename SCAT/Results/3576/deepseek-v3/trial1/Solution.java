class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        Set<Integer>[] adj = new Set[n];
        Set<Integer>[] adj2 = new Set[n];

        for(int i = 0; i < n; i++) {
            adj[i] = new HashSet();
            adj2[i] = new HashSet();
        }

        for(int i = 1; i < n; i++) {
            adj[parent[i]].add(i);
            adj2[parent[i]].add(i);
        }

       // System.out.println(adj2[1].toString());

        dfs(0, adj, s, new Integer[27], -1, adj2);
    
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

    void dfs(int node, Set<Integer>[] adj, String s, Integer[] closest, int parent, Set<Integer>[] adj2) {
        int val = s.charAt(node) - 'a';

        if (closest[val] != null) {
            int newParent = closest[val];
            if (parent != -1) {
                adj2[parent].remove(node);
            }
            adj2[newParent].add(node);
        } 

        Integer temp = closest[val];

        closest[val] = node;

        for(int neigh : adj[node]) {
            dfs(neigh, adj, s, closest, node, adj2);
        }

        closest[val] = temp;
    }
}