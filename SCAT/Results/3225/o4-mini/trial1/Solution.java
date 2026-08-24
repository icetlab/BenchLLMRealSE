class Solution {

    public int maxSubarrayLength(int[] nums, int k) {
        int ans = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        int left = 0;
        for(int right = 0;right < nums.length;right++) {
            int cnt = map.getOrDefault(nums[right], 0) + 1;
            map.put(nums[right], cnt);

            while (cnt > k) {
                if (nums[left] == nums[right]) {
                    cnt--;
                }
                map.put(nums[left], map.get(nums[left]) - 1);
                if (map.get(nums[left]) == 0)
                    map.remove(nums[left]);
                left++;
            }

            ans = Math.max(ans, right - left + 1);

        }

        return ans;

    }
}