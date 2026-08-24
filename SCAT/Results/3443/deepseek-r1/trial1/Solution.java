import java.util.Arrays;

class Solution {
    public int maxTotalReward(int[] rewardValues) {
        int[] arr = rewardValues.clone();
        Arrays.sort(arr);
        int m = 0;
        for (int i = 0; i < arr.length; i++) {
            if (m == 0 || arr[i] != arr[m - 1]) {
                arr[m++] = arr[i];
            }
        }
        if (m == 0) {
            return 0;
        }

        int maxReward = arr[m - 1];
        long[] bits = new long[Math.max(1, (int) ((2L * maxReward + 63) >>> 6))];
        bits[0] = 1L;

        for (int i = 0; i < m; i++) {
            orShiftedLeft(bits, arr[i]);
        }

        for (int i = bits.length - 1; i >= 0; i--) {
            if (bits[i] != 0) {
                return (i << 6) + (63 - Long.numberOfLeadingZeros(bits[i]));
            }
        }
        return 0;
    }

    private static void orShiftedLeft(long[] bits, int shift) {
        if (shift == 0) {
            return;
        }
        int wordShift = shift >>> 6;
        int bitShift = shift & 63;
        int limit = (shift - 1) >>> 6;

        for (int i = 0; i <= limit; i++) {
            long source = bits[i];
            if (i == limit && bitShift != 0) {
                source &= (1L << bitShift) - 1;
            }
            if (source == 0) {
                continue;
            }

            bits[i + wordShift] |= source << bitShift;
            if (bitShift != 0) {
                long carry = source >>> (64 - bitShift);
                if (carry != 0) {
                    bits[i + wordShift + 1] |= carry;
                }
            }
        }
    }
}