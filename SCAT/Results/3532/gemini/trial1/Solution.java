import java.util.ArrayList;
import java.util.List;  

class Solution {
    private List<Integer>[] links;
    private int[][] signs;

    private void determine(int index, int parent, int previous) {
        int max1 = 0;
        int max2 = 0;
        int max1_node = -1;

        for (int child : links[index]) {
            if (child == parent) continue;

            int weight = (child % 2 == 0) ? 2 : 1;
            int other = weight + signs[child][0];

            if (other > max1) {
                max2 = max1;
                max1 = other;
                max1_node = child;
            } else if (other > max2) {
                max2 = other;
            }
        }

        signs[index][1] = Math.max(previous, max1);

        for (int child : links[index]) {
            if (child == parent) continue;

            int cost_to_parent = (index % 2 == 0) ? 2 : 1;

            int up_val;
            if (child == max1_node) {
                up_val = max2;
            } else {
                up_val = max1;
            }

            int reach = cost_to_parent + Math.max(previous, up_val);
            determine(child, index, reach);
        }
    }

    private int traverse(int index, int parent) {
        int higher = 0;
        for (int neighbor : links[index]) {
            if (neighbor == parent) continue;

            int temps = traverse(neighbor, index);

            int weight = (neighbor % 2 == 0) ? 2 : 1;
            higher = Math.max(higher, temps + weight);
        }
        signs[index][0] = higher;
        return higher;
    }

    public int[] timeTaken(int[][] edges) {
        int spans = edges.length + 1;
        links = new ArrayList[spans];
        signs = new int[spans][2];
        for (int i = 0; i < spans; i++) {
            links[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            links[edge[0]].add(edge[1]);
            links[edge[1]].add(edge[0]);
        }

        traverse(0, -1);
        determine(0, -1, 0);

        int[] buffer = new int[spans];
        for (int i = 0; i < spans; i++) {
            buffer[i] = signs[i][1];
        }
        return buffer;
    }
}