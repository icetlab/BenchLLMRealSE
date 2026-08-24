package effibenchjmh.task3563.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

import java.util.Map;
import java.util.HashMap;

class Solution {

    private TreeMap<Integer, Set<Integer>> createTreeMap(List<List<Integer>> grid) {
        TreeMap<Integer, Set<Integer>> tm = new TreeMap<>();
        for (int rIdx = 0, n = grid.size(); rIdx < n; rIdx++) {
            List<Integer> g = grid.get(rIdx);
            for (int cIdx = 0, m = g.size(); cIdx < m; cIdx++) {
                int ele = g.get(cIdx);
                tm.computeIfAbsent(ele, k -> new HashSet<>()).add(rIdx);
            }
        }
        return tm;
    }

    private Map<Integer, Map<Set<Integer>, Integer>> memo;

    private int maxHelper(TreeMap<Integer, Set<Integer>> tm, Integer num, Set<Integer> vis) {
        Integer curr = tm.lowerKey(num);
        if (curr == null) {
            return 0;
        }

        if (memo.containsKey(num) && memo.get(num).containsKey(vis)) {
            return memo.get(num).get(vis);
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
        int result = flg ? max + curr : maxHelper(tm, curr, vis);

        memo.computeIfAbsent(num, k -> new HashMap<>()).put(new HashSet<>(vis), result);

        return result;
    }

    public int maxScore(List<List<Integer>> grid) {
        TreeMap<Integer, Set<Integer>> tm = createTreeMap(grid);
        memo = new HashMap<>();
        return maxHelper(tm, tm.lastKey() + 1, new HashSet<>());
    }
}
