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
        int[] path = new int[n];
        DFS(0, -1, adj, nums, 0, 0, 0, map, path);
        return new int[]{maxLen, minNodes};
    }

    public void DFS(int node, int parent, ArrayList<ArrayList<int[]>> adj, int[] nums,
                    int pathSum, int start, int end, Map<Integer, Integer> map, int[] path) {

        int prevDepth = map.getOrDefault(nums[node], -1);
        while (start <= prevDepth) {
            pathSum -= path[start++];
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
                path[end] = edge[1];
                DFS(edge[0], node, adj, nums, pathSum + edge[1], start, end + 1, map, path);
            }
        }

        if (prevDepth == -1) map.remove(nums[node]);
        else map.put(nums[node], prevDepth);
    }
}
