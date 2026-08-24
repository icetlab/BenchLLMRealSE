class Solution {
    private static final int BASE = (1 << 17) - 1;
    
    public int[] minimumCost(int n, int[][] edges, int[][] query) {
        boolean[] visited = new boolean[n];
        ArrayList<ArrayList<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            int s = edge[0];
            int d = edge[1];
            int w = edge[2];
            adj.get(s).add(new int[]{d, w});
            adj.get(d).add(new int[]{s, w});
        }

        int[] vertexForestMap = new int[n];
        int[] forestCost = new int[n];
        int forestId = 0;
        
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                int cost = dfs(adj, visited, vertexForestMap, i, forestId);
                forestCost[forestId] = cost;
                forestId++;
            }
        }

        int[] answer = new int[query.length];
        for (int j = 0; j < query.length; j++) {
            int[] q = query[j];
            int queryAns = -1;
            if (vertexForestMap[q[0]] == vertexForestMap[q[1]]) {
                queryAns = forestCost[vertexForestMap[q[0]]];
            }
            answer[j] = queryAns;
        }

        return answer;
    }

    public int dfs(ArrayList<ArrayList<int[]>> adj, boolean[] visited, int[] vertexForestMap, int src, int forestId) {
        vertexForestMap[src] = forestId;
        visited[src] = true;
        int cost = BASE;
        
        for (int[] node : adj.get(src)) {
            int neighbor = node[0];
            int weight = node[1];
            cost = cost & weight;
            if (!visited[neighbor]) {
                cost = cost & dfs(adj, visited, vertexForestMap, neighbor, forestId);
            }
        }
        return cost;
    }
}