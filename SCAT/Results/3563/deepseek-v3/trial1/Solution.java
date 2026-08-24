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

    private int maxHelper(TreeMap<Integer, Set<Integer>> tm, Integer num, int visMask, java.util.Map<Integer, Integer> memo) {
        Integer curr = tm.lowerKey(num);
        if (curr == null) {
            return 0;
        }

        // Memoization key: combine current value and visited mask
        int key = (curr << 16) | visMask; // use 16 bits for mask to avoid overflow
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        Set<Integer> rSet = tm.get(curr);
        boolean flg = false;
        int max = 0;
        for (int rs : rSet) {
            if ((visMask & (1 << rs)) != 0) {
                continue;
            }
            flg = true;
            max = Math.max(max, maxHelper(tm, curr, visMask | (1 << rs), memo));
        }
        int result = flg ? max + curr : maxHelper(tm, curr, visMask, memo);
        memo.put(key, result);
        return result;
    }

    public int maxScore(List<List<Integer>> grid) {
        TreeMap<Integer, Set<Integer>> tm = createTreeMap(grid);
        java.util.Map<Integer, Integer> memo = new java.util.HashMap<>();
        return maxHelper(tm, tm.lastKey() + 1, 0, memo);
    }
}