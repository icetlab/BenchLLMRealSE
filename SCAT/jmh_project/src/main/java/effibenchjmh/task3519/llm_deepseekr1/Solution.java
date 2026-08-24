package effibenchjmh.task3519.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int winningPlayerCount(int n, int[][] pick) {
    boolean[] won = new boolean[n];
    int winningCount = 0;
    Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
    for (int[] arr : pick) {
        int player = arr[0];
        int color = arr[1];
        Map<Integer, Integer> colorCount = map.computeIfAbsent(player, k -> new HashMap<>());
        int newCount = colorCount.merge(color, 1, Integer::sum);
        if (!won[player] && newCount == player + 1) {
            won[player] = true;
            winningCount++;
        }
    }
    return winningCount;
}
}
