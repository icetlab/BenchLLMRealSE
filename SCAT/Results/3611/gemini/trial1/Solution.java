class Solution {
    public static int[] minBitwiseArray(List<Integer> nums) {
        int n = nums.size();
        int[] ans = new int[n];
        for (int j = 0; j < n; j++) {
            int num = nums.get(j);

            if (num == 1 || num == 2) {
                ans[j] = -1;
                continue;
            }

            // A positive number has all bits set up to its MSB if (x & (x + 1)) == 0.
            if ((num & (num + 1)) == 0) {
                // This is the "AllsetBit" case. Flip the most significant bit.
                ans[j] = num ^ Integer.highestOneBit(num);
                continue;
            }

            // Default to original number, update if a flippable bit is found.
            ans[j] = num;
            
            // General case: Find the highest set bit 'p' (not MSB) 
            // such that all bits below p are also set. Then flip 'p'.
            int msb = Integer.highestOneBit(num);
            for (int p = msb >> 1; p > 0; p >>= 1) {
                if ((num & p) != 0) { // If bit at p is set
                    int lower_mask = p - 1;
                    if ((num & lower_mask) == lower_mask) { // And all lower bits are set
                        ans[j] = num ^ p; // Flip this bit
                        break; // Found the highest one, so we can stop.
                    }
                }
            }
        }

        return ans;
    }
}