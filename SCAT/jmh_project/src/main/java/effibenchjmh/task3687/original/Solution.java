package effibenchjmh.task3687.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    int maxLen = 0;
    int minNodes = Integer.MAX_VALUE;

    public int[] longestSpecialPath(int[][] edges, int[] nums) {
        int n = nums.length;
        ArrayList<ArrayList<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        for (int[] edge : edges) {
            adj.get(edge[0]).add(new int[]{edge[1], edge[2]});
            adj.get(edge[1]).add(new int[]{edge[0], edge[2]});
        }

        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> path = new ArrayList<>();
        DFS(0, -1, adj, nums, 0, 0, 0, map, path);
        return new int[]{maxLen, minNodes};
    }

    public void DFS(int node, int parent, ArrayList<ArrayList<int[]>> adj, int[] nums,
                    int pathSum, int start, int end, Map<Integer, Integer> map, List<Integer> path) {

        int prevDepth = map.getOrDefault(nums[node], -1);
        while (start <= prevDepth) {
            pathSum -= path.get(start++);
        }

        if (pathSum > maxLen) {
            maxLen = pathSum;
            minNodes = end - start + 1;
        } else if (pathSum == maxLen) {
            minNodes = Math.min(minNodes, end - start + 1);
        }

        map.put(nums[node], end);

        for (int[] edge : adj.get(node)) {
            if (edge[0] != parent) {
                path.add(edge[1]);
                DFS(edge[0], node, adj, nums, pathSum + edge[1], start, end + 1, map, path);
                path.remove(path.size() - 1);
            }
        }

        if (prevDepth == -1) map.remove(nums[node]);
        else map.put(nums[node], prevDepth);
    }
}

