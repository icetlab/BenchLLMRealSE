package effibenchjmh.task3519.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int winningPlayerCount(int n, int[][] pick) {
        int count=0;
        boolean[] counted = new boolean[n];
        HashMap<Integer,Integer>[] countMap = new HashMap[n];
        for(int[] arr : pick) {
            int i = arr[0], j = arr[1];
            if (counted[i]) continue;
            if (countMap[i] == null) countMap[i] = new HashMap<>();
            HashMap<Integer,Integer> h = countMap[i];
            int freq = h.getOrDefault(j, 0) + 1;
            h.put(j, freq);
            if (freq >= i + 1) {
                counted[i] = true;
                count++;
            }
        }

        return count;
    }
}
