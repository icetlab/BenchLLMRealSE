import java.util.*;

class Solution {
    long[] ans;

    ArrayList<Long> fn(int source, ArrayList<ArrayList<Integer>> G, int[] vis, int[] cost) {
        vis[source] = 1;
        ArrayList<Long> local = new ArrayList<>();
        local.add((long) cost[source]);

        ArrayList<Integer> children = G.get(source);
        for (int child : children) {
            if (vis[child] == -1) {
                local.addAll(fn(child, G, vis, cost));
            }
        }

        long max1 = Long.MIN_VALUE, max2 = Long.MIN_VALUE, max3 = Long.MIN_VALUE;
        long min1 = Long.MAX_VALUE, min2 = Long.MAX_VALUE;
        for (long v : local) {
            if (v > max1) {
                max3 = max2;
                max2 = max1;
                max1 = v;
            } else if (v > max2) {
                max3 = max2;
                max2 = v;
            } else if (v > max3) {
                max3 = v;
            }

            if (v < min1) {
                min2 = min1;
                min1 = v;
            } else if (v < min2) {
                min2 = v;
            }
        }

        if (local.size() < 3) {
            ans[source] = 1;
        } else {
            long first = max1 * (max2 * max3);
            long last = min1 * min2 * max1;
            ans[source] = Math.max(first, last);
            if (ans[source] < 0) {
                ans[source] = 0;
            }
        }

        vis[source] = 2;
        if (local.size() <= 5) {
            return local;
        }

        ArrayList<Long> trimmed = new ArrayList<>();
        trimmed.add(min1);
        trimmed.add(min2);
        trimmed.add(max3);
        trimmed.add(max2);
        trimmed.add(max1);
        return trimmed;
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
        ArrayList<Long> res = fn(0, G, vis, cost);
        return ans;
    }
}