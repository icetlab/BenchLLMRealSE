package effibenchjmh.task3350.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
        private static class FenwickTree {
        private final int[] tree;
        private final int size;

        public FenwickTree(int size) {
            this.size = size;
            this.tree = new int[size + 1];
        }

        public void update(int index, int delta) {
            index++;
            while (index <= size) {
                tree[index] += delta;
                index += index & -index;
            }
        }

        public int query(int index) {
            index++;
            int sum = 0;
            while (index > 0) {
                sum += tree[index];
                index -= index & -index;
            }
            return sum;
        }
    }

    public int[] resultArray(int[] nums) {
        int n = nums.length;

        var uniqueNumsSet = new HashSet<Integer>();
        for (int num : nums) {
            uniqueNumsSet.add(num);
        }
        var sortedUniqueNums = new ArrayList<>(uniqueNumsSet);
        Collections.sort(sortedUniqueNums);
        var rankMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < sortedUniqueNums.size(); i++) {
            rankMap.put(sortedUniqueNums.get(i), i);
        }
        int distinctCount = sortedUniqueNums.size();

        var n1 = new ArrayList<Integer>();
        var n2 = new ArrayList<Integer>();
        
        var bit1 = new FenwickTree(distinctCount);
        var bit2 = new FenwickTree(distinctCount);

        n1.add(nums[0]);
        bit1.update(rankMap.get(nums[0]), 1);
        
        n2.add(nums[1]);
        bit2.update(rankMap.get(nums[1]), 1);

        for (int i = 2; i < n; i++) {
            int v = nums[i];
            int rank = rankMap.get(v);

            int leqCount1 = bit1.query(rank);
            int gct1 = n1.size() - leqCount1;

            int leqCount2 = bit2.query(rank);
            int gct2 = n2.size() - leqCount2;

            if (gct1 > gct2 || (gct1 == gct2 && n1.size() <= n2.size())) {
                n1.add(v);
                bit1.update(rank, 1);
            } else {
                n2.add(v);
                bit2.update(rank, 1);
            }
        }

        var ans = new int[n];
        int idx = 0;
        for (int num : n1) {
            ans[idx++] = num;
        }
        for (int num : n2) {
            ans[idx++] = num;
        }
        return ans;
    }
}
