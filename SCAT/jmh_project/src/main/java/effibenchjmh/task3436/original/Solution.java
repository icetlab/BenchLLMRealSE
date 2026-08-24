package effibenchjmh.task3436.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int minimumDifference(int[] nums, int k) {
        SegmentTree seg = new SegmentTree(0 , nums.length);
        int ans = Integer.MAX_VALUE;
        for(int i = 0;i < nums.length;i++)
            seg.update(i , nums[i]);
        
        for(int i = 0;i < nums.length;i++) {
            int low = i;
            int high = nums.length;
            
            
            while(low <= high) {
                int mid = (low + high) / 2;
                //System.out.println(i + " " + mid);
                int rangeOr = seg.query(i , mid);
                ans = Math.min(Math.abs(k - rangeOr) , ans);
                if(k == rangeOr) {
                    ans = 0;
                    return 0;
                }
                else if(rangeOr < k)
                    low = mid + 1;
                else 
                    high = mid - 1;

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
