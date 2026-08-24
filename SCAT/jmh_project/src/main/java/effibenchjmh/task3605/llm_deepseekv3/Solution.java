package effibenchjmh.task3605.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int[] minBitwiseArray(List<Integer> nums) {
        int[] arr = new int[nums.size()];
        for (int i = 0; i < nums.size(); i++) {
            int n = nums.get(i);
            // j|(j+1) is always odd, so even n has no solution
            if ((n & 1) == 0) {
                arr[i] = -1;
                continue;
            }
            // Count trailing 1s in n
            int k = 0;
            int temp = n;
            while ((temp & 1) == 1) {
                k++;
                temp >>= 1;
            }
            // j = n with the (k-1)-th bit cleared (since bits 0..k-1 are all 1 in n)
            arr[i] = n - (1 << (k - 1));
        }
        return arr;
    }
}
