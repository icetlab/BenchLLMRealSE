package effibenchjmh.task3563.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

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
        return maxHelper(tm, tm.lastKey() + 1, new HashSet<>());
    }
}
