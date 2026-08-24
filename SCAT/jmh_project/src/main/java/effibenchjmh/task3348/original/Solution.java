package effibenchjmh.task3348.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    int BASE = (int)Math.pow(2, 17) - 1;
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

        // ArrayList<HashSet<Integer>> forest = new ArrayList<>();
        // ArrayList<Integer> minCost = new ArrayList<>();
        int[] vertexForestMap = new int[n];
        int[] forestCost = new int[n];
        int forestId=0;
        // HashMap<Integer, Integer> vertexForestMap = new HashMap<>();
        for (int i=0; i<n; i++) {
            if (!visited[i]) {
                // HashSet<Integer> vertices = new HashSet<>();
                // int[] cost = {BASE};
                int cost = dfs(adj, visited, vertexForestMap, i, forestId);
                forestCost[forestId] = cost;
                forestId++;
                // forest.add(vertices);
                // minCost.add(cost[0]);
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

    public int dfs(ArrayList<ArrayList<int[]>> adj, boolean[] visited, int[] vertexForestMap, int src, int forestId) {
        int cost = Integer.MAX_VALUE;
        if (visited[src]) {
            return cost;
        }
        vertexForestMap[src] = forestId;
        visited[src] = true;
        
        for (int[] node: adj.get(src)) {
            // if (!visited[node[0]]) {
                cost = cost & node[1] & dfs(adj, visited, vertexForestMap, node[0], forestId);
            // }
        }
        return cost;
    }
}
