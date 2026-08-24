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
        if(nums == null || nums.length == 0) return 0;
        if(numOperations < 0) return 0;
        if(nums.length == 1) return 1;

        java.util.Map<Integer, Integer> freqMap = new java.util.HashMap<>();
        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        java.util.Set<Integer> pointSet = new java.util.HashSet<>();
        for (int num : nums) {
            pointSet.add(num);
            pointSet.add(num - k);
            pointSet.add(num + k);
        }
        
        java.util.List<Integer> points = new java.util.ArrayList<>(pointSet);
        java.util.Collections.sort(points);
        int m = points.size();

        int[] prefixSum = new int[m];
        for (int i = 0; i < m; i++) {
            prefixSum[i] = freqMap.getOrDefault(points.get(i), 0);
            if (i > 0) {
                prefixSum[i] += prefixSum[i - 1];
            }
        }

        int res = 0;
        // Optimization: use two pointers to find the range for each target value.
        // This avoids repeated logarithmic searches in the main loop.
        int ceil_ptr = 0;
        int floor_ptr = 0;
        for (int i = 0; i < m; i++) {
            int v = points.get(i);
            long lower_bound = (long) v - k;
            long upper_bound = (long) v + k;

            while (ceil_ptr < m && points.get(ceil_ptr) < lower_bound) {
                ceil_ptr++;
            }
            
            while (floor_ptr < m && points.get(floor_ptr) <= upper_bound) {
                floor_ptr++;
            }
            
            int ceil_idx = ceil_ptr;
            int floor_idx = floor_ptr - 1;
            
            // As v is always in its own range [v-k, v+k], floor_idx will be >= ceil_idx.
            // So, no need to check for an empty range.
            
            int sum = prefixSum[floor_idx] - (ceil_idx == 0 ? 0 : prefixSum[ceil_idx - 1]);
            int v_freq = freqMap.getOrDefault(v, 0);
            
            // Number of elements we can convert to v is sum - v_freq.
            // We can perform at most numOperations conversions.
            int currOp = Math.min(numOperations, sum - v_freq);
            res = Math.max(res, v_freq + currOp);
        }
        return res;
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