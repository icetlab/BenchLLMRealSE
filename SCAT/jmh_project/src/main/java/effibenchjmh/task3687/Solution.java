package effibenchjmh.task3687;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    int globalMax;
    int globalMin;
    int[] lastOccurrence;
    int[] pathSum;
    int[] nums;
    public int[] longestSpecialPath(int[][] edges, int[] nums) {
        int n = nums.length;
        this.nums = nums;
        @SuppressWarnings("unchecked")
        ArrayList<Edge>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            graph[u].add(new Edge(v, w));
            graph[v].add(new Edge(u, w));
        }
        @SuppressWarnings("unchecked")
        ArrayList<Edge>[] children = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            children[i] = new ArrayList<>();
        }
        buildTree(0, -1, graph, children);
        globalMax = 0;
        globalMin = Integer.MAX_VALUE;
        lastOccurrence = new int[50001];
        for (int i = 0; i < 50001; i++) {
            lastOccurrence[i] = -1;
        }
        pathSum = new int[n];
        dfs(0, 0, 0, 0, children);
        return new int[]{globalMax, globalMin};
    }
    void dfs(int node, int depth, int curSum, int windowStart, ArrayList<Edge>[] children) {
        int val = nums[node];
        int oldOccurrence = lastOccurrence[val];
        int newWindowStart = windowStart;
        if (oldOccurrence >= windowStart) {
            newWindowStart = oldOccurrence + 1;
        }
        lastOccurrence[val] = depth;
        pathSum[depth] = curSum;
        int candidate = curSum - pathSum[newWindowStart];
        int candidateCount = depth - newWindowStart + 1;
        if (candidate > globalMax) {
            globalMax = candidate;
            globalMin = candidateCount;
        } else if (candidate == globalMax) {
            if (candidateCount < globalMin) globalMin = candidateCount;
        }
        for (Edge edge : children[node]) {
            dfs(edge.child, depth + 1, curSum + edge.weight, newWindowStart, children);
        }
        lastOccurrence[val] = oldOccurrence;
    }
    void buildTree(int node, int parent, ArrayList<Edge>[] graph, ArrayList<Edge>[] children) {
        for (Edge edge : graph[node]) {
            if (edge.child != parent) {
                children[node].add(new Edge(edge.child, edge.weight));
                buildTree(edge.child, node, graph, children);
            }
        }
    }
    class Edge {
        int child;
        int weight;
        Edge(int child, int weight) {
            this.child = child;
            this.weight = weight;
        }
    }
}
