class Solution {

    public int maxSubarrayLength(int[] nums, int k) {
        int ans = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        int left = 0;
        for(int right = 0;right < nums.length;right++) {
            map.put(nums[right], map.getOrDefault(nums[right], 0) + 1);

            while(map.get(nums[right]) > k) {
                map.compute(nums[left], (key, val) -> val == 1 ? null : val - 1);
                left++;
            }

            ans = Math.max(ans, right - left + 1);

        }

        return ans;

    }
}