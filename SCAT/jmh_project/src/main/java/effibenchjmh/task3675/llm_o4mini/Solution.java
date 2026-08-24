package effibenchjmh.task3675.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

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
        int childCount = gr.get(node).size() - (parent == -1 ? 0 : 1);
        long sumRemove = 0;
        long[] diffs = new long[childCount];
        int idx = 0;
        for (Edge edge : gr.get(node)) {
            int ne = edge.neighbour, wt = edge.weight;
            if (ne != parent) {
                long dontRemove = solve(ne, 0, node) + wt;
                long remove = solve(ne, 1, node);
                sumRemove += remove;
                diffs[idx++] = dontRemove - remove;
            }
        }
        Arrays.sort(diffs);
        long extra = 0;
        for (int i = childCount - 1; i >= edgesToRemove; i--) {
            if (diffs[i] > 0) extra += diffs[i];
        }
        answer += sumRemove + extra;
        return dp[node][isParEdgeRemoved] = answer;
    }
}
