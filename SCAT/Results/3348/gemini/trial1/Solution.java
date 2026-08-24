class Solution {
    public int[] minimumCost(int n, int[][] edges, int[][] query) {
        boolean[] visited = new boolean[n];
        ArrayList<ArrayList<int[]>> adj = new ArrayList<>();
        for (int i=0; i<n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int i=0; i<edges.length; i++) {
            int s = edges[i][0];
            int d = edges[i][1];
            int w = edges[i][2];
            adj.get(s).add(new int[]{d, w});
            adj.get(d).add(new int[]{s, w});
        }

        int[] vertexForestMap = new int[n];
        int[] forestCost = new int[n];
        java.util.Arrays.fill(forestCost, Integer.MAX_VALUE);
        int forestId=0;
        for (int i=0; i<n; i++) {
            if (!visited[i]) {
                dfs(adj, visited, vertexForestMap, i, forestId, forestCost);
                forestId++;
            }
        }

        int[] answer = new int[query.length];
        for (int j=0; j<query.length; j++) {
            int[] q = query[j];
            int queryAns = -1;
            if (vertexForestMap[q[0]] == vertexForestMap[q[1]]) {
                queryAns = forestCost[vertexForestMap[q[0]]];
            }
            answer[j] = queryAns;
        }

        return answer;
    }

    public void dfs(ArrayList<ArrayList<int[]>> adj, boolean[] visited, int[] vertexForestMap, int src, int forestId, int[] forestCost) {
        visited[src] = true;
        vertexForestMap[src] = forestId;
        
        for (int[] edge: adj.get(src)) {
            forestCost[forestId] &= edge[1];
            int neighbor = edge[0];
            if (!visited[neighbor]) {
                dfs(adj, visited, vertexForestMap, neighbor, forestId, forestCost);
            }
        }
    }
}