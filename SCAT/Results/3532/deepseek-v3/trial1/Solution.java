import java.util.*;  

class Solution {
    private List<Integer>[] links;
    private boolean[] visited;
    private int[][] signs;  

    private void determine(int index, int previous) {
        visited[index] = true;
        List<Integer> neighbors = links[index];
        
        int max1 = 0, max2 = 0;
        int max1Child = -1;
        
        for (int child : neighbors) {
            if (visited[child]) continue;
            int val = (child % 2 == 0 ? 2 : 1) + signs[child][0];
            if (val > max1) {
                max2 = max1;
                max1 = val;
                max1Child = child;
            } else if (val > max2) {
                max2 = val;
            }
        }
        
        signs[index][1] = Math.max(previous, max1);
        
        for (int child : neighbors) {
            if (visited[child]) continue;
            int add = (index % 2 == 0) ? 2 : 1;
            int reach = (child == max1Child) 
                ? Math.max(max2, previous) + add
                : Math.max(max1, previous) + add;
            determine(child, reach);
        }
    } 
    private int traverse(int index) {
        visited[index] = true;
        int higher = 0;
        for (int child : links[index]) {
            if (visited[child]) continue;
            int val = traverse(child);
            higher = Math.max(higher, val + (child % 2 == 0 ? 2 : 1));
        }
        signs[index][0] = higher;
        visited[index] = false;
        return higher;
    }
    public int[] timeTaken(int[][] edges) {
        int n = edges.length + 1;
        links = new ArrayList[n];
        visited = new boolean[n];
        signs = new int[n][2];
        
        for (int i = 0; i < n; i++) {
            links[i] = new ArrayList<>();
        }
        
        for (int[] edge : edges) {
            links[edge[0]].add(edge[1]);
            links[edge[1]].add(edge[0]);
        }
        
        traverse(0);
        determine(0, 0);
        
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = signs[i][1];
        }
        return result;
    }
}