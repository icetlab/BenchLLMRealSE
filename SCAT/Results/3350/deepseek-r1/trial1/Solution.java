import java.util.*;

class Solution {
    public int[] resultArray(int[] nums) {
        int n = nums.length;
        // coordinate compression
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        Map<Integer, Integer> rank = new HashMap<>();
        for (int v : sorted) {
            rank.putIfAbsent(v, rank.size());
        }
        int m = rank.size();
        
        Fenwick ft1 = new Fenwick(m);
        Fenwick ft2 = new Fenwick(m);
        
        List<Integer> arr1 = new ArrayList<>();
        List<Integer> arr2 = new ArrayList<>();
        
        arr1.add(nums[0]);
        ft1.add(rank.get(nums[0]), 1);
        arr2.add(nums[1]);
        ft2.add(rank.get(nums[1]), 1);
        
        int total1 = 1, total2 = 1;
        
        for (int i = 2; i < n; i++) {
            int v = nums[i];
            int idx = rank.get(v);
            int gct1 = total1 - ft1.prefixSum(idx);
            int gct2 = total2 - ft2.prefixSum(idx);
            
            if (gct1 > gct2 || (gct1 == gct2 && arr1.size() <= arr2.size())) {
                arr1.add(v);
                ft1.add(idx, 1);
                total1++;
            } else {
                arr2.add(v);
                ft2.add(idx, 1);
                total2++;
            }
        }
        
        int[] ans = new int[n];
        int pos = 0;
        for (int x : arr1) ans[pos++] = x;
        for (int x : arr2) ans[pos++] = x;
        return ans;
    }
    
    private static class Fenwick {
        private int[] tree;
        private int n;
        
        Fenwick(int n) {
            this.n = n;
            this.tree = new int[n + 1];
        }
        
        void add(int idx, int delta) {
            idx++; // 1-indexed
            while (idx <= n) {
                tree[idx] += delta;
                idx += idx & -idx;
            }
        }
        
        int prefixSum(int idx) {
            idx++; // 1-indexed
            int sum = 0;
            while (idx > 0) {
                sum += tree[idx];
                idx -= idx & -idx;
            }
            return sum;
        }
    }
}