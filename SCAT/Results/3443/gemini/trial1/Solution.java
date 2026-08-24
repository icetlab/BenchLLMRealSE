import java.math.BigInteger;
import java.util.Arrays;

class Solution {
    public int maxTotalReward(int[] rewardValues) {
        int[] nums = Arrays.stream(rewardValues).distinct().sorted().toArray();

        // The problem is equivalent to finding the maximum sum of a subset of `nums`
        // where for any chosen reward `v`, the sum of previously chosen smaller rewards is less than `v`.
        // This can be solved using dynamic programming with a bitmask, where the k-th bit is 1
        // if a sum of `k` is achievable.
        // The original solution uses BigInteger, which is slow. We can use a long[] array for the bitmask
        // as the maximum possible sum is bounded by 2 * max(rewardValues), which is < 100,000.

        final int MAX_POSSIBLE_SUM = 100000;
        long[] bits = new long[(MAX_POSSIBLE_SUM + 63) / 64];
        bits[0] = 1L; // Sum 0 is achievable initially

        for (int v : nums) {
            int v_word_idx = v / 64;
            int v_bit_idx = v % 64;

            // We iterate from right to left to avoid using sums that are newly added in this same step.
            // We only need to consider achievable sums `r < v`, so we iterate up to word index `v_word_idx`.
            for (int i = v_word_idx; i >= 0; i--) {
                long word = bits[i];

                if (i == v_word_idx) {
                    // Mask to only include sums `r < v`.
                    word &= (1L << v_bit_idx) - 1;
                }

                if (word == 0) {
                    continue;
                }

                // Shift the achievable sums by `v` and OR them back into the bitmask.
                int target_word_idx = i + v_word_idx;
                if (v_bit_idx == 0) {
                    if (target_word_idx < bits.length) {
                        bits[target_word_idx] |= word;
                    }
                } else {
                    if (target_word_idx < bits.length) {
                        bits[target_word_idx] |= word << v_bit_idx;
                    }
                    if (target_word_idx + 1 < bits.length) {
                        bits[target_word_idx + 1] |= word >>> (64 - v_bit_idx);
                    }
                }
            }
        }

        // Find the highest set bit, which corresponds to the maximum achievable sum.
        for (int i = bits.length - 1; i >= 0; i--) {
            if (bits[i] != 0) {
                return i * 64 + (63 - Long.numberOfLeadingZeros(bits[i]));
            }
        }
        return 0;
    }
}