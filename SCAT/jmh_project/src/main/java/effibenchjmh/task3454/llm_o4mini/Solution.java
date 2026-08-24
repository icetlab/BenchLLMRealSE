package effibenchjmh.task3454.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public long minimumOperations(int[] nums, int[] target) {
        int n = nums.length;
        long add = 0;
        int prevNum = 0, prevTarget = 0;
        for (int i = 0; i <= n; i++) {
            int curNum = (i < n ? nums[i] : 0);
            int curTarget = (i < n ? target[i] : 0);
            int diff1 = curNum - prevNum;
            int diff2 = curTarget - prevTarget;
            if (diff1 < diff2) {
                add += diff2 - diff1;
            }
            prevNum = curNum;
            prevTarget = curTarget;
        }
        return add;
    }
}
