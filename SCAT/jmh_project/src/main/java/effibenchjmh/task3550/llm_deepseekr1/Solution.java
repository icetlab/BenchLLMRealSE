package effibenchjmh.task3550.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

import java.util.*;

class Solution {

    long solve(int[][] arr, int row, int mask, int rooks, Map<String, Long>dp)
    {
        int n = arr.length;
        int m = arr[0].length;
        if(rooks == 0)
        {  
            return 0;    
        }
        if(row >= n)
        {
            return Integer.MIN_VALUE;
        }
        String key = row + "-" + rooks + "-" + mask;
        if(dp.containsKey(key))
            return dp.get(key);
        long ans = solve(arr, row + 1, mask, rooks, dp);
        for(int i = 0; i < m; i++)
        {
            if((mask & (1 << i)) == 0)
            {
                long temp =  solve(arr, row + 1, mask | (1 << i), rooks - 1, dp);
                if(temp != Integer.MIN_VALUE)
                    ans = Math.max(ans, arr[row][i] + temp);
            }

        }
        dp.put(key, ans);
        return ans;      
    }
    public long maximumValueSum(int[][] board) {
        int m = board.length;
        int n = board[0].length;
        if (m < 3 || n < 3) {
            return -3000000000L;
        }

        class Edge {
            int to, rev, cap;
            long cost;

            Edge(int to, int rev, int cap, long cost) {
                this.to = to;
                this.rev = rev;
                this.cap = cap;
                this.cost = cost;
            }
        }

        int rows = m;
        int cols = n;
        int S = rows + cols;
        int T = S + 1;
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i <= T; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < rows; i++) {
            List<Edge> from = graph.get(S);
            List<Edge> to = graph.get(i);
            from.add(new Edge(i, to.size(), 1, 0));
            to.add(new Edge(S, from.size() - 1, 0, 0));
        }
        for (int j = 0; j < cols; j++) {
            int cnode = rows + j;
            List<Edge> from = graph.get(cnode);
            List<Edge> to = graph.get(T);
            from.add(new Edge(T, to.size(), 1, 0));
            to.add(new Edge(cnode, from.size() - 1, 0, 0));
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int cnode = rows + j;
                List<Edge> from = graph.get(i);
                List<Edge> to = graph.get(cnode);
                from.add(new Edge(cnode, to.size(), 1, -(long) board[i][j]));
                to.add(new Edge(i, from.size() - 1, 0, (long) board[i][j]));
            }
        }

        int N = T + 1;
        long inf = Long.MAX_VALUE / 4;
        long[] pot = new long[N];
        Arrays.fill(pot, inf);
        pot[S] = 0;
        for (int i = 0; i < rows; i++) {
            pot[i] = 0;
        }
        for (int j = 0; j < cols; j++) {
            long best = inf;
            for (int i = 0; i < rows; i++) {
                best = Math.min(best, -(long) board[i][j]);
            }
            pot[rows + j] = best;
        }
        pot[T] = inf;
        for (int j = 0; j < cols; j++) {
            pot[T] = Math.min(pot[T], pot[rows + j]);
        }

        int flow = 0;
        long totalCost = 0;
        while (flow < 3) {
            long[] dist = new long[N];
            Arrays.fill(dist, inf);
            int[] pv = new int[N];
            int[] pe = new int[N];
            dist[S] = 0;
            PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[1], b[1]));
            pq.offer(new long[]{S, 0});

            while (!pq.isEmpty()) {
                long[] cur = pq.poll();
                int v = (int) cur[0];
                long d = cur[1];
                if (d != dist[v]) {
                    continue;
                }
                List<Edge> edges = graph.get(v);
                for (int idx = 0; idx < edges.size(); idx++) {
                    Edge e = edges.get(idx);
                    if (e.cap <= 0) {
                        continue;
                    }
                    long nd = d + e.cost + pot[v] - pot[e.to];
                    if (nd < dist[e.to]) {
                        dist[e.to] = nd;
                        pv[e.to] = v;
                        pe[e.to] = idx;
                        pq.offer(new long[]{e.to, nd});
                    }
                }
            }

            if (dist[T] == inf) {
                break;
            }

            for (int v = 0; v < N; v++) {
                if (dist[v] < inf) {
                    pot[v] += dist[v];
                }
            }

            int add = 3 - flow;
            for (int v = T; v != S; v = pv[v]) {
                add = Math.min(add, graph.get(pv[v]).get(pe[v]).cap);
            }
            for (int v = T; v != S; v = pv[v]) {
                Edge e = graph.get(pv[v]).get(pe[v]);
                e.cap -= add;
                graph.get(v).get(e.rev).cap += add;
            }

            flow += add;
            totalCost += add * (pot[T] - pot[S]);
        }

        return -totalCost;
    }
}
