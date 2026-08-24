class Solution {
    public int[] minBitwiseArray(List<Integer> nums) {
        int[] arr = new int[nums.size()];
        for (int i = 0; i < nums.size(); i++) {
            int x = nums.get(i);
            // count trailing ones in x
            int m = 0;
            int tmp = x;
            while ((tmp & 1) == 1) {
                m++;
                tmp >>>= 1;
            }
            if (m == 0) {
                arr[i] = -1;
            } else {
                int high = x >>> m;
                // rebuild j: high bits of x plus (m-1) ones at LSB
                arr[i] = (high << m) | ((1 << (m - 1)) - 1);
            }
        }
        return arr;
    }
}