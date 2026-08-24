package effibenchjmh.task3443.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

import java.util.Arrays;
import java.util.BitSet;

class Solution {
    public int maxTotalReward(int[] rewardValues) {
        int[] nums = Arrays.stream(rewardValues).distinct().sorted().toArray();
        int sum = 0;
        for (int v : nums) sum += v;
        BitSet bs = new BitSet(sum + 1);
        bs.set(0);
        int ans = 0;
        for (int v : nums) {
            BitSet lower = bs.get(0, v);
            for (int s = lower.nextSetBit(0); s >= 0; s = lower.nextSetBit(s + 1)) {
                bs.set(s + v);
            }
            ans = Math.max(ans, bs.length() - 1);
        }
        return ans;
    }
}
