class Solution {
    public int minimumSubarrayLength(int[] nums, int k) {
        int n = nums.length;
        int result = Integer.MAX_VALUE;

        int i = 0;
        int j = 0;

        // bitCounts stores the count of set bits for each position in the current window.
        // currentOr stores the bitwise OR of all numbers in the current window.
        int[] bitCounts = new int[32];
        int currentOr = 0;

        while (j < n) {
            // Add nums[j] to the window and update bit counts and currentOr.
            currentOr |= nums[j];
            addNumberToBitCounts(nums[j], bitCounts);

            // Shrink the window from the left while the OR value is still >= k.
            while (i <= j && currentOr >= k) {
                result = Math.min(result, j - i + 1);

                // Remove nums[i] from the window and update bit counts and currentOr.
                currentOr = removeNumberFromBitCounts(nums[i], bitCounts, currentOr);
                i++;
            }

            j++;
        }

        return result == Integer.MAX_VALUE ? -1 : result;
    }

    // Increments bit counts for the given number.
    private void addNumberToBitCounts(int number, int[] bitCounts) {
        for (int i = 0; i < 32; i++) {
            if (((number >> i) & 1) == 1) {
                bitCounts[i]++;
            }
        }
    }

    // Decrements bit counts and updates the window's OR value when a number is removed.
    private int removeNumberFromBitCounts(int number, int[] bitCounts, int currentOr) {
        for (int i = 0; i < 32; i++) {
            if (((number >> i) & 1) == 1) {
                bitCounts[i]--;
                if (bitCounts[i] == 0) {
                    // This was the last number in the window with this bit set,
                    // so unset this bit from the current OR value.
                    currentOr &= ~(1 << i);
                }
            }
        }
        return currentOr;
    }
}