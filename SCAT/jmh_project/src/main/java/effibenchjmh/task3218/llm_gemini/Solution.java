package effibenchjmh.task3218.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    long[] ans;

    ArrayList<Long> fn(int source, ArrayList<ArrayList<Integer>> G, int[] vis, int[] cost) {
        vis[source] = 1;
        ArrayList<Long> local = new ArrayList<>();
        local.add((long) cost[source]);
        ArrayList<Integer> children = G.get(source);
        for (int j = 0; j < children.size(); j++) {
            if (vis[children.get(j)] == -1) {
                ArrayList<Long> res = fn(children.get(j), G, vis, cost);
                local.addAll(res);
            }
        }
        if (local.size() <= 5) { // If small, original logic is fine and fast.
            Collections.sort(local);
            if (local.size() < 3) {
                ans[source] = 1;
            } else {
                long first = local.get(local.size() - 1) *
                        (local.get(local.size() - 2) * local.get(local.size() - 3));
                long last = local.get(0) * local.get(1) * local.get(local.size() - 1);
                ans[source] = Math.max(first, last);
                if(ans[source]<0){
                    ans[source]=0;
                }
            }
            vis[source] = 2;
            return local;
        } else { // If large, use linear scan to find extremes instead of sorting.
            long max1 = Long.MIN_VALUE, max2 = Long.MIN_VALUE, max3 = Long.MIN_VALUE;
            long min1 = Long.MAX_VALUE, min2 = Long.MAX_VALUE;

            for (long val : local) {
                if (val >= max1) {
                    max3 = max2;
                    max2 = max1;
                    max1 = val;
                } else if (val >= max2) {
                    max3 = max2;
                    max2 = val;
                } else if (val > max3) {
                    max3 = val;
                }

                if (val <= min1) {
                    min2 = min1;
                    min1 = val;
                } else if (val < min2) {
                    min2 = val;
                }
            }

            long first = max1 * max2 * max3;
            long last = min1 * min2 * max1;
            ans[source] = Math.max(first, last);
            if (ans[source] < 0) {
                ans[source] = 0;
            }

            vis[source] = 2;
            
            ArrayList<Long> res = new ArrayList<>();
            res.add(min1);
            res.add(min2);
            res.add(max3);
            res.add(max2);
            res.add(max1);
            Collections.sort(res);
            return res;
        }
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
