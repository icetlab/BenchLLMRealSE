package effibenchjmh.task3454.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public long minimumOperations(int[] nums, int[] target) {
        long add = 0, sub = 0;
        int prevNum = 0, prevTarget = 0;
        for (int i = 0; i < nums.length; i++) {
            int diff1 = nums[i] - prevNum;
            int diff2 = target[i] - prevTarget;
            if (diff1 < diff2) {
                add += (diff2 - diff1);
            } else {
                sub += (diff1 - diff2);
            }
            prevNum = nums[i];
            prevTarget = target[i];
        }
        return Math.max(add, sub);
    }
}
