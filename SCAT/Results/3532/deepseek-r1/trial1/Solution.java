import java.util.ArrayList;
import java.util.Arrays;

class Solution {
    public static ArrayList<Integer>[] links;
    public static int[][] signs;  

    public static void determine(int index, int previous, int parent) {
        int max1 = 0, max2 = 0;
        int bestChild = -1;
        for (int child : links[index]) {
            if (child == parent) continue;
            int val = signs[child][0] + (child % 2 == 1 ? 1 : 2);
            if (val > max1) {
                max2 = max1;
                max1 = val;
                bestChild = child;
            } else if (val > max2) {
                max2 = val;
            }
        }
        signs[index][1] = Math.max(previous, max1);
        for (int child : links[index]) {
            if (child == parent) continue;
            int reach = (index % 2 == 0) ? 2 : 1;
            reach += (child == bestChild) ? Math.max(max2, previous) : Math.max(max1, previous);
            determine(child, reach, index);
        }
    }
    public static int traverse(int index, int parent) {
        int higher = 0;
        for (int child : links[index]) {
            if (child == parent) continue;
            int temps = traverse(child, index);
            if (child % 2 == 1) higher = Math.max(temps + 1, higher);
            else higher = Math.max(temps + 2, higher);
        }
        signs[index][0] = higher;
        return higher;
    }
    public int[] timeTaken(int[][] edges) {
        int spans = edges.length+1 ; 
        links = new ArrayList[spans];
        signs = new int[spans][2];
        for (int fe = 0; fe < spans; fe++) {
            links[fe] = new ArrayList<Integer>();
            signs[fe] = new int[]{0, 0};
        }
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            links[u].add(v);
            links[v].add(u);
        }
         traverse(0, -1);
         determine(0, 0, -1);
       // System.out.println(Arrays.deepToString(signs)) ; 

        int[] buffer = new int[ spans] ;   
        for (int fh=spans-1;fh>=0;fh--){
            buffer[fh] = signs[fh][1] ; 
        }
        return buffer ;  
    }
}