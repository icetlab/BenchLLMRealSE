package effibenchjmh.task3622.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    // https://leetcode.com/problems/frequency-of-the-most-frequent-element/description/
    // 題目跟上面類似 但做法不一樣 原因是最大freuency現在不一定是 1,4或5了
    // case1 [1, 4   ,5], k = 1, numOperations = 2
    // case2 [50, 80] k=20, numOperations = 2, 現在最大frequency為60-70 (50+10, 80-20)...etc

    // {1-> 1, 4->1, 5->1}   建構 treemap 
    //  0,1,2                建構 val->id map  
    // [1,2,3]               建構 prefixSum
    // 最後iterate nums, 假如現在是4   treemap找4+1 floor, 4-1 ceil, 可以用logN 方式找到需要多少operation

   public int maxFrequency(int[] nums, int k, int numOperations) {
        if (nums == null || nums.length == 0) return 0;
        if (numOperations < 0) return 0;
        if (nums.length == 1) return 1;

        int n = nums.length;
        java.util.Arrays.sort(nums);

        int[] candidates = new int[n * 3];
        int m = 0;
        for (int num : nums) {
            candidates[m++] = num;
            candidates[m++] = num + k;
            candidates[m++] = num - k;
        }
        java.util.Arrays.sort(candidates);

        int ans = 0;
        int left = 0;
        int right = 0;
        int eq = 0;

        for (int idx = 0; idx < m; ) {
            int x = candidates[idx];
            int low = x - k;
            int high = x + k;

            while (left < n && nums[left] < low) left++;
            while (right < n && nums[right] <= high) right++;

            while (eq < n && nums[eq] < x) eq++;
            int eqStart = eq;
            while (eq < n && nums[eq] == x) eq++;
            int equal = eq - eqStart;

            int within = right - left;
            int added = Math.min(numOperations, within - equal);
            ans = Math.max(ans, equal + added);

            if (ans == n) return n;

            while (idx < m && candidates[idx] == x) idx++;
        }

        return ans;
    }

    /*
    // 用 range based 來解決 case 2 問題
    public int maxFrequency(int[] nums, int k, int numOperations) {
        if(nums == null || nums.length == 0) return 0;
        if(numOperations < 0) return 0;
        if(nums.length == 1) return 1;

        int res = 0;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        TreeMap<Integer, Integer> freq = new TreeMap<>(); // nums[i] -> freq
        HashMap<Integer, Integer> valToId = new HashMap<>();

        for(int i=0; i<nums.length; i++){
            freq.put(nums[i], freq.getOrDefault(nums[i], 0) + 1);
            max = Math.max(max, nums[i]);
            min = Math.min(min, nums[i]);
        }

        int[] prefixSum = new int[freq.size()];
        int idx = 0;
        for(int key: freq.keySet()){
            valToId.put(key, idx);
            prefixSum[idx] = freq.get(key) + (idx == 0 ? 0: prefixSum[idx-1]);
            idx++;
        }

        for(int i=min; i<=max; i++){
            Integer floor = freq.floorKey(i + k);
            Integer ceil = freq.ceilingKey(i - k);

            int idx2 = valToId.get(floor);
            int idx1 = valToId.get(ceil);

            int sum = prefixSum[idx2] - (idx1 == 0 ? 0: prefixSum[idx1-1]);
            int currOp = Math.min(numOperations, sum - freq.getOrDefault(i, 0));

            res = Math.max(res, freq.getOrDefault(i, 0) + currOp);
        }
        return res;
    }
    */
}
