class Solution {
    public int[] minimumCost(int n, int[][] edges, int[][] query) {
        int[] parent = new int[n];
        int[] size = new int[n];
        int[] compAnd = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
            compAnd[i] = Integer.MAX_VALUE; // identity for AND
        }
        
        // Union-Find with path compression, union by size, and component AND maintenance
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], w = edge[2];
            int pu = find(parent, u);
            int pv = find(parent, v);
            if (pu != pv) {
                // union by size
                if (size[pu] < size[pv]) {
                    int temp = pu;
                    pu = pv;
                    pv = temp;
                }
                parent[pv] = pu;
                size[pu] += size[pv];
                compAnd[pu] = compAnd[pu] & compAnd[pv] & w;
            } else {
                // edge inside same component – include its weight
                compAnd[pu] = compAnd[pu] & w;
            }
        }
        
        int[] answer = new int[query.length];
        for (int i = 0; i < query.length; i++) {
            int u = query[i][0], v = query[i][1];
            if (find(parent, u) == find(parent, v)) {
                answer[i] = compAnd[find(parent, u)];
            } else {
                answer[i] = -1;
            }
        }
        return answer;
    }
    
    private int find(int[] parent, int x) {
        if (parent[x] != x) {
            parent[x] = find(parent, parent[x]);
        }
        return parent[x];
    }
}