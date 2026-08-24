package effibenchjmh.task3454.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public long minimumOperations(int[] nums, int[] target) {
        long add = 0;
        int prevNum = 0;
        int prevTarget = 0;
        int len = nums.length;
        

        

        for (int i = 0; i < len; i++) {
            int diffNum = nums[i] - prevNum;
            int diffTarget = target[i] - prevTarget;
            if (diffNum < diffTarget) {
                add += diffTarget - diffNum;
            }
            prevNum = nums[i];
            prevTarget = target[i];
        }
        int lastDiffNum = -prevNum;
        int lastDiffTarget = -prevTarget;
        if (lastDiffNum < lastDiffTarget) {
            add += lastDiffTarget - lastDiffNum;
        }
        return add;
    }
}
