class Solution {

    private TreeMap<Integer, Set<Integer>> createTreeMap(List<List<Integer>> grid) {
        TreeMap<Integer, Set<Integer>> tm = new TreeMap<>();
        for (int rIdx = 0, n = grid.size(); rIdx < n; rIdx++) {
            List<Integer> g = grid.get(rIdx);
            for (int cIdx = 0, m = g.size(); cIdx < m; cIdx++) {
                int ele = g.get(cIdx);
                if (!tm.containsKey(ele)) {
                    tm.put(ele, new HashSet<>());
                }
                tm.get(ele).add(rIdx);
            }
        }
        return tm;
    }

    private int maxHelper(TreeMap<Integer, Set<Integer>> tm, Integer num, Set<Integer> vis) {
        Integer curr = tm.lowerKey(num);
        if (curr == null) {
            return 0;
        }

        Set<Integer> rSet = tm.get(curr);
        boolean flg = false;
        int max = 0;
        for (int rs : rSet) {
            if (vis.contains(rs)) {
                continue;
            }
            flg = true;
            vis.add(rs);
            max = Math.max(max, maxHelper(tm, curr, vis));
            vis.remove(rs);
        }
        return flg ? max + curr : maxHelper(tm, curr, vis);
    }

    public int maxScore(List<List<Integer>> grid) {
        TreeMap<Integer, Set<Integer>> tm = createTreeMap(grid);
        int R = grid.size();
        List<Integer> keys = new ArrayList<>(tm.descendingKeySet());
        int K = keys.size();
        int maxMask = 1 << R;
        int[] dp = new int[maxMask];
        // initialize unreachable states to -inf
        for (int mask = 1; mask < maxMask; mask++) {
            dp[mask] = Integer.MIN_VALUE;
        }
        // for each value (weight) in descending order, try to assign it to any free row
        for (int i = 0; i < K; i++) {
            int w = keys.get(i);
            Set<Integer> rows = tm.get(w);
            // traverse masks downward to avoid reuse within this iteration
            for (int mask = maxMask - 1; mask >= 0; mask--) {
                if (dp[mask] < 0) continue;  // unreachable
                for (int r : rows) {
                    if (((mask >> r) & 1) == 0) {
                        int newMask = mask | (1 << r);
                        dp[newMask] = Math.max(dp[newMask], dp[mask] + w);
                    }
                }
            }
        }
        // answer is the max over all masks
        int res = 0;
        for (int v : dp) {
            res = Math.max(res, v);
        }
        return res;
    }
}