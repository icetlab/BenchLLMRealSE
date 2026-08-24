package effibenchjmh.task3687.llm_deepseekv3;

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

        int[] last = new int[100001];
        java.util.Arrays.fill(last, -1);
        int[] path = new int[n];
        dfs(0, -1, adj, nums, 0, 0, 0, last, path);
        return new int[]{maxLen, minNodes};
    }

    private void dfs(int node, int parent, ArrayList<ArrayList<int[]>> adj, int[] nums,
                     int pathSum, int start, int end, int[] last, int[] path) {

        int prevDepth = last[nums[node]];
        while (start <= prevDepth) {
            pathSum -= path[start];
            start++;
        }

        if (pathSum > maxLen) {
            maxLen = pathSum;
            minNodes = end - start + 1;
        } else if (pathSum == maxLen) {
            minNodes = Math.min(minNodes, end - start + 1);
        }

        last[nums[node]] = end;

        for (int[] edge : adj.get(node)) {
            if (edge[0] != parent) {
                path[end] = edge[1];
                dfs(edge[0], node, adj, nums, pathSum + edge[1], start, end + 1, last, path);
            }
        }

        if (prevDepth == -1) last[nums[node]] = -1;
        else last[nums[node]] = prevDepth;
    }
}

