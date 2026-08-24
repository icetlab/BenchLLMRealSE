class Solution {
    static class Edge {
        int neighbour;
        int weight;

        Edge(int neighbour, int weight) {
            this.neighbour = neighbour;
            this.weight = weight;
        }
    }

    private long[][] dp;
    private List<List<Edge>> gr;
    private int k;

    public long maximizeSumOfWeights(int[][] edges, int k) {
        int n = edges.length + 1;
        this.k = k;
        gr = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            gr.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            gr.get(edge[0]).add(new Edge(edge[1], edge[2]));
            gr.get(edge[1]).add(new Edge(edge[0], edge[2]));
        }
        dp = new long[n][2];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1L);
        }
        return solve(0, 0, -1);
    }

    private long solve(int node, int isParEdgeRemoved, int parent) {
        if (dp[node][isParEdgeRemoved] != -1) return dp[node][isParEdgeRemoved];

        int edgesToRemove = Math.max(0, gr.get(node).size() - k - isParEdgeRemoved);
        long answer = 0;
        List<long[]> candidates = new ArrayList<>();
        for (Edge edge : gr.get(node)) {
            int ne = edge.neighbour, wt = edge.weight;
            if (ne != parent) {
                long dontRemove = solve(ne, 0, node) + wt;
                long remove = solve(ne, 1, node);
                candidates.add(new long[]{dontRemove, remove});
            }
        }

        candidates.sort((p1, p2) -> Long.compare(p1[0] - p1[1], p2[0] - p2[1]));
        for (int i = 0; i < candidates.size(); i++) {
            long dontRemove = candidates.get(i)[0];
            long remove = candidates.get(i)[1];
            if (i < edgesToRemove) {
                answer += remove;
            } else {
                answer += Math.max(dontRemove, remove);
            }
        }
        return dp[node][isParEdgeRemoved] = answer;
    }
}