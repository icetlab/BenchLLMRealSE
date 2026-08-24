package effibenchjmh.task3436.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int minimumDifference(int[] nums, int k) {
        int ans = Integer.MAX_VALUE;
        java.util.List<Integer> prev = new java.util.ArrayList<>();
        for (int num : nums) {
            java.util.Set<Integer> currSet = new java.util.HashSet<>();
            currSet.add(num);
            for (int x : prev) {
                currSet.add(x | num);
            }
            prev = new java.util.ArrayList<>(currSet);
            for (int val : prev) {
                ans = Math.min(ans, Math.abs(val - k));
                if (ans == 0) return 0;
            }
        }
        return ans;
    }
    class SegmentTree {
        final int treeFrom;
        final int length;
        final int[] val;

        public SegmentTree(int treeFrom, int treeTo) {
            this.treeFrom = treeFrom;
            int size = treeTo - treeFrom + 1;
            int l = 0;
            for (; (1 << l) < size; l++);
            this.length = 1 << l;
            val = new int[1 << (l + 1)];
        }

        public void update(int index, int delta) {
            int node = index + length - treeFrom;
            val[node] = delta;
            for (node >>= 1; node > 0; node >>= 1) {
                val[node] = comb(val[node << 1], val[(node << 1) + 1]);
            }
        }

        public int query(int from, int to) {
            
            from += length - treeFrom;
            to += length - treeFrom + 1;
            int left = 0;
            int right = 0;
            
            while (from < to) {
                if ((from & 1) == 1) left = comb(left, val[from++]);
                if ((to & 1) == 1) right = comb(val[--to], right);
                from >>= 1;
                to >>= 1;
            }
            return comb(left , right);
    }

        public int comb(int a, int b) {
            return a | b; 
        }
    }
}
