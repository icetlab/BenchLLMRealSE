package effibenchjmh.task3381;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    //https://stackoverflow.com/questions/26727044/how-to-reverse-a-bitwise-or-operation

    public void doBitWiseOrOperation(int[] bitCount, int[] orValWrapper, int n) {
        orValWrapper[0] = (orValWrapper[0] | n);
        for(int i = 0; i < 32; i++) {
            bitCount[i] += ((n & (1 << i)) != 0 ? 1 : 0);
        }
    }

    public void undoBitWiseOrOperation(int[] bitCount, int[] orValWrapper, int n) {
        for(int i = 0; i < 32; i++) {
            bitCount[i] += ((n & (1 << i)) != 0 ? -1 : 0);
            if (bitCount[i] == 0) orValWrapper[0] = orValWrapper[0] & (~(1 << i));
        }
    }

    public int minimumSubarrayLength(int[] nums, int k) {
        int orVal = 0;
        int minLength = Integer.MAX_VALUE;
        int[] bitCount = new int[32];
        int[] orValWrapper = {orVal};
        for(int start = 0, end = 0; end < nums.length; end++) {
            doBitWiseOrOperation(bitCount, orValWrapper, nums[end]);
            if(orValWrapper[0] < k) continue;
            for(; start <= end && orValWrapper[0] >= k; start++) {
                undoBitWiseOrOperation(bitCount, orValWrapper, nums[start]);
                minLength = Math.min(minLength, end - start + 1);
            }
        }
        return (minLength == Integer.MAX_VALUE) ? -1 : minLength;
    }
}
