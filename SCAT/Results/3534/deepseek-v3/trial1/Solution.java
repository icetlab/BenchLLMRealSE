class Solution {
    public int countPairs(int[] nums) {
        int cnt=0;
        int n = nums.length;
        // Precompute all single-swap variants for each number
        Set<Integer>[] variants = new Set[n];
        for (int i = 0; i < n; i++) {
            variants[i] = getVariants(nums[i]);
        }
        
        for(int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){
                // Check if numbers are equal OR one is a single-swap variant of the other
                if (nums[i] == nums[j] || variants[i].contains(nums[j]) || variants[j].contains(nums[i])) {
                    cnt++;
                }
            }
        }
        return cnt;
    }
    
    private Set<Integer> getVariants(int num) {
        Set<Integer> set = new HashSet<>();
        String s = Integer.toString(num);
        set.add(num);
        int len = s.length();
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                char[] arr = s.toCharArray();
                char tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
                set.add(Integer.parseInt(new String(arr)));
            }
        }
        return set;
    }
}