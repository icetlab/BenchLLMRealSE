class Solution {
    public int minimumSumSubarray(List<Integer> nums, int l, int r) 
    {
        int size = nums.size();
        int[] prefix = new int[size + 1];
        for (int i = 0; i < size; i++) {
            prefix[i + 1] = prefix[i] + nums.get(i);
        }
        int res = -1;
        for (int s = l; s <= r; s++) {
            for (int i = 0; i <= size - s; i++) {
                int sum = prefix[i + s] - prefix[i];
                if (sum > 0) {
                    if (res == -1 || res > sum) res = sum;
                }
            }
        }
        return res;
    }
}