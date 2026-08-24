package effibenchjmh.task3687.llm_gemini;

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
        if (n == 0) {
            return new int[]{0, 0};
        }
        ArrayList<ArrayList<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        for (int[] edge : edges) {
            adj.get(edge[0]).add(new int[]{edge[1], edge[2]});
            adj.get(edge[1]).add(new int[]{edge[0], edge[2]});
        }

        Map<Integer, Integer> valToDepth = new HashMap<>();
        List<Integer> prefixSums = new ArrayList<>();
        prefixSums.add(0);

        DFS(0, -1, adj, nums, 0, 0, 0, valToDepth, prefixSums);

        return new int[]{maxLen, minNodes};
    }

    public void DFS(int node, int parent, ArrayList<ArrayList<int[]>> adj, int[] nums,
                    int startDepth, int depth, int prefixSum,
                    Map<Integer, Integer> valToDepth, List<Integer> prefixSums) {

        int prevDepth = valToDepth.getOrDefault(nums[node], -1);

        int newStartDepth = Math.max(startDepth, prevDepth + 1);

        int currentPathSum = prefixSum - prefixSums.get(newStartDepth);
        int currentPathNodes = depth - newStartDepth + 1;

        if (currentPathSum > maxLen) {
            maxLen = currentPathSum;
            minNodes = currentPathNodes;
        } else if (currentPathSum == maxLen) {
            minNodes = Math.min(minNodes, currentPathNodes);
        }

        Integer oldDepthForVal = valToDepth.put(nums[node], depth);

        for (int[] edge : adj.get(node)) {
            int neighbor = edge[0];
            int weight = edge[1];
            if (neighbor != parent) {
                prefixSums.add(prefixSum + weight);
                DFS(neighbor, node, adj, nums, newStartDepth, depth + 1, prefixSum + weight, valToDepth, prefixSums);
                prefixSums.remove(prefixSums.size() - 1);
            }
        }

        if (oldDepthForVal == null) {
            valToDepth.remove(nums[node]);
        } else {
            valToDepth.put(nums[node], oldDepthForVal);
        }
    }
}

