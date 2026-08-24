package effibenchjmh.task3687.llm_deepseekr1;

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
        class IntMap {
            int[] keys;
            int[] vals;
            byte[] state;
            int mask;

            IntMap(int n) {
                int cap = 1;
                int need = n * 2;
                while (cap < need) cap <<= 1;
                keys = new int[cap];
                vals = new int[cap];
                state = new byte[cap];
                mask = cap - 1;
            }

            int hash(int x) {
                int h = x * 0x9e3779b1;
                return h ^ (h >>> 16);
            }

            int get(int key) {
                int idx = hash(key) & mask;
                while (state[idx] != 0) {
                    if (state[idx] == 1 && keys[idx] == key) return vals[idx];
                    idx = (idx + 1) & mask;
                }
                return -1;
            }

            void put(int key, int value) {
                int idx = hash(key) & mask;
                while (state[idx] == 1) {
                    if (keys[idx] == key) {
                        vals[idx] = value;
                        return;
                    }
                    idx = (idx + 1) & mask;
                }
                state[idx] = 1;
                keys[idx] = key;
                vals[idx] = value;
            }
        }

        int n = nums.length;
        maxLen = 0;
        minNodes = Integer.MAX_VALUE;

        int[] head = new int[n];
        for (int i = 0; i < n; i++) head[i] = -1;

        int m = edges.length;
        int[] to = new int[2 * m];
        int[] wt = new int[2 * m];
        int[] nxt = new int[2 * m];
        int eidx = 0;
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], w = edge[2];
            to[eidx] = v;
            wt[eidx] = w;
            nxt[eidx] = head[u];
            head[u] = eidx++;
            to[eidx] = u;
            wt[eidx] = w;
            nxt[eidx] = head[v];
            head[v] = eidx++;
        }

        IntMap last = new IntMap(n);
        int[] path = new int[n];

        int[] stNode = new int[n];
        int[] stParent = new int[n];
        int[] stSum = new int[n];
        int[] stStart = new int[n];
        int[] stEnd = new int[n];
        int[] stPrev = new int[n];
        int[] stEdge = new int[n];

        int rootPrev = last.get(nums[0]);
        if (0 == maxLen) {
            minNodes = Math.min(minNodes, 1);
        }
        last.put(nums[0], 0);

        stNode[0] = 0;
        stParent[0] = -1;
        stSum[0] = 0;
        stStart[0] = 0;
        stEnd[0] = 0;
        stPrev[0] = rootPrev;
        stEdge[0] = head[0];
        int top = 1;

        while (top > 0) {
            int idx = top - 1;
            int e = stEdge[idx];
            if (e != -1) {
                stEdge[idx] = nxt[e];

                int u = stNode[idx];
                int v = to[e];
                if (v == stParent[idx]) continue;

                int pSum = stSum[idx];
                int pStart = stStart[idx];
                int pEnd = stEnd[idx];

                path[pEnd] = wt[e];

                int cEnd = pEnd + 1;
                int cStart = pStart;
                int cSum = pSum + wt[e];
                int cPrev = last.get(nums[v]);
                while (cStart <= cPrev) {
                    cSum -= path[cStart];
                    cStart++;
                }

                int nodes = cEnd - cStart + 1;
                if (cSum > maxLen) {
                    maxLen = cSum;
                    minNodes = nodes;
                } else if (cSum == maxLen && nodes < minNodes) {
                    minNodes = nodes;
                }

                last.put(nums[v], cEnd);

                stNode[top] = v;
                stParent[top] = u;
                stSum[top] = cSum;
                stStart[top] = cStart;
                stEnd[top] = cEnd;
                stPrev[top] = cPrev;
                stEdge[top] = head[v];
                top++;
            } else {
                int u = stNode[idx];
                last.put(nums[u], stPrev[idx]);
                top--;
            }
        }

        return new int[]{maxLen, minNodes};
    }
}

