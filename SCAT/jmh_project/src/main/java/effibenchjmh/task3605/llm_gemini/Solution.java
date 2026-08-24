package effibenchjmh.task3605.llm_gemini;

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
            int num = nums.get(i);
            if ((num & 1) == 0) {
                arr[i] = -1;
            } else {
                arr[i] = num - (Integer.lowestOneBit(num + 1) / 2);
            }
        }
        return arr;
    }
}
