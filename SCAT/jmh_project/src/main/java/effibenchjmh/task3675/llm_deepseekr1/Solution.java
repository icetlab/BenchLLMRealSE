package effibenchjmh.task3675.llm_deepseekr1;

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

    public long maximizeSumOfWeights(int[][] edges, int k) {
        int n = edges.length + 1;
        if (k <= 0) return 0L;

        int[] head = new int[n];
        java.util.Arrays.fill(head, -1);
        int edgeCount = n - 1;
        int[] to = new int[2 * edgeCount];
        int[] next = new int[2 * edgeCount];
        int[] weight = new int[2 * edgeCount];
        int eidx = 0;

        for (int[] edge : edges) {
            int a = edge[0], b = edge[1], w = edge[2];
            to[eidx] = b;
            weight[eidx] = w;
            next[eidx] = head[a];
            head[a] = eidx++;

            to[eidx] = a;
            weight[eidx] = w;
            next[eidx] = head[b];
            head[b] = eidx++;
        }

        int[] parent = new int[n];
        java.util.Arrays.fill(parent, -1);
        int[] order = new int[n];
        int top = 0;
        order[top++] = 0;
        parent[0] = -2;

        for (int i = 0; i < top; i++) {
            int v = order[i];
            for (int e = head[v]; e != -1; e = next[e]) {
                int u = to[e];
                if (u == parent[v]) continue;
                if (parent[u] == -1) {
                    parent[u] = v;
                    order[top++] = u;
                }
            }
        }

        long[] dp0 = new long[n];
        long[] dp1 = new long[n];
        long[] gains = new long[n];

        for (int idx = top - 1; idx >= 0; idx--) {
            int v = order[idx];
            int degree = 0;
            int childCount = 0;
            long sumRemoved = 0L;

            for (int e = head[v]; e != -1; e = next[e]) {
                degree++;
                int u = to[e];
                if (parent[u] == v) {
                    long remove = dp1[u];
                    long keep = dp0[u] + weight[e];
                    gains[childCount++] = keep - remove;
                    sumRemoved += remove;
                }
            }

            if (childCount > 1) {
                java.util.Arrays.sort(gains, 0, childCount);
            }

            int r0 = degree - k;
            if (r0 < 0) r0 = 0;
            int r1 = degree - k - 1;
            if (r1 < 0) r1 = 0;
            if (v == 0) r1 = r0;

            long ans0 = sumRemoved;
            for (int i = r0; i < childCount; i++) {
                if (gains[i] > 0) ans0 += gains[i];
            }
            dp0[v] = ans0;

            long ans1 = sumRemoved;
            for (int i = r1; i < childCount; i++) {
                if (gains[i] > 0) ans1 += gains[i];
            }
            dp1[v] = ans1;
        }

        return dp0[0];
    }
}
