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
                for (int k = 0; k < res.size(); k++) {
                    local.add(res.get(k));
                }
            }
        }
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
        if (local.size() <= 5) {
            return local;
        }
        ArrayList<Long> res = new ArrayList<>();
        res.add(local.get(0));
        res.add(local.get(1));
        res.add(local.get(local.size() - 3));
        res.add(local.get(local.size() - 2));
        res.add(local.get(local.size() - 1));
        return res;
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