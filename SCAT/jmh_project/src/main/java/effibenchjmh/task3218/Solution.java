package effibenchjmh.task3218;

import java.util.*;
import java.util.stream.*;

class X {
    // number of nodes in subtree
    public int numNodes;
    // posMax: [maximum, second maximum, third maximum]
    // negMin: [minimum, second minimum]
    public ArrayList<Integer> posMax, negMin;

    // default constructor
    public X() {
        numNodes = 0;
        posMax = new ArrayList<>();
        negMin = new ArrayList<>();
    }

    // constructor with one cost value (or one tree node)
    public X(int val) {
        numNodes = 1;
        posMax = new ArrayList<>();
        negMin = new ArrayList<>();
        if (val > 0) {
            posMax.add(val);
        } else {
            negMin.add(val);
        }
    }

    // update current node with child's information
    public void update(X x) {
        // add child's nodes
        numNodes += x.numNodes;

        // add child's maximum(s) and minimum(s)
        posMax.addAll(x.posMax);
        negMin.addAll(x.negMin);

        // sort them and select top 3 maximum and top 2 minimum
        posMax.sort(Collections.reverseOrder());
        negMin.sort(null);
        while (posMax.size() > 3) {
            posMax.remove(posMax.size() - 1);
        }
        while (negMin.size() > 2) {
            negMin.remove(negMin.size() - 1);
        }
    }

    // maximum product of subtree
    public long product() {
        // case 1
        if (numNodes < 3) {
            return 1;
        }
        // case 2
        long result = 0;
        // choice 1 (all positive)
        if (posMax.size() == 3) {
            result = (long)posMax.get(0) * posMax.get(1) * posMax.get(2);
        }
        // choice 2 (2 negative 1 positive)
        if (negMin.size() == 2 && !posMax.isEmpty()) {
            result = Math.max(result, (long)negMin.get(0) * negMin.get(1) * posMax.get(0));
        }
        return result;
    }
}

class Solution {
    // Depth-First Search on tree
    // fills coins array
    X fillCoins(int n, ArrayList<ArrayList<Integer>> graph, int[] cost, int curr, int prev, long[] coins) {
        // maintain information of current subtree
        X x = new X(cost[curr]);
        for (int next : graph.get(curr)) {
            // avoid calling function back to parent
            if (next != prev) {
                // fillCoins() returns information of child's subtree
                // update current subtree with child's information
                x.update(fillCoins(n, graph, cost, next, curr, coins));
            }
        }
        // once information of current subtree is completed
        // calculate maximum product
        coins[curr] = x.product();
        return x;
    }

    public long[] placedCoins(int[][] edges, int[] cost) {
        int n = cost.length;
        // make tree in adjacency list form
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        long[] coins = new long[n];
        // call DFS and fill coins array
        fillCoins(n, graph, cost, 0, -1, coins);
        return coins;
    }
}
