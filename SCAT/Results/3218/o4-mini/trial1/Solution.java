import java.util.*;

class Solution {
    private static class NodeRes {
        long min1, min2, max1, max2, max3;
        int count;
        NodeRes(long value) {
            min1 = value;
            min2 = Long.MAX_VALUE;
            max1 = value;
            max2 = Long.MIN_VALUE;
            max3 = Long.MIN_VALUE;
            count = 1;
        }
    }
    long[] ans;

    NodeRes fn(int source, ArrayList<ArrayList<Integer>> G, int[] vis, int[] cost) {
        vis[source] = 1;
        NodeRes local = new NodeRes(cost[source]);
        ArrayList<Integer> children = G.get(source);
        for (int child : children) {
            if (vis[child] == -1) {
                NodeRes res = fn(child, G, vis, cost);
                local.count += res.count;
                long[] minC = {local.min1, local.min2, res.min1, res.min2};
                Arrays.sort(minC);
                local.min1 = minC[0];
                local.min2 = minC[1];
                long[] maxC = {local.max1, local.max2, local.max3, res.max1, res.max2, res.max3};
                Arrays.sort(maxC);
                local.max1 = maxC[5];
                local.max2 = maxC[4];
                local.max3 = maxC[3];
            }
        }
        if (local.count < 3) {
            ans[source] = 1;
        } else {
            long first = local.max1 * local.max2 * local.max3;
            long last = local.min1 * local.min2 * local.max1;
            ans[source] = Math.max(Math.max(first, last), 0L);
        }
        vis[source] = 2;
        return local;
    }

    public long[] placedCoins(int[][] edges, int[] cost) {
        int n = edges.length + 1;
        ans = new long[n];
        ArrayList<ArrayList<Integer>> G = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < n; i++) {
            G.add(new ArrayList<>());
        }
        int[] vis = new int[n];
        Arrays.fill(vis, -1);
        for (int[] edge : edges) {
            G.get(edge[0]).add(edge[1]);
            G.get(edge[1]).add(edge[0]);
        }
        NodeRes root = fn(0, G, vis, cost);
        return ans;
    }
}