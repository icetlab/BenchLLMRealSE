package effibenchjmh.task3619.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

import java.util.*;

class Solution {
    public int maxIncreasingSubarrays(List<Integer> nums) {
        int n = nums.size();
        

        int prevLen = 0;
        int max = 0;
        int i = 0;
        while (i < n) {
            int start = i;
            i++;
            while (i < n && nums.get(i) > nums.get(i - 1)) {
                i++;
            }
            int len = i - start;
            max = Math.max(max, len / 2);
            max = Math.max(max, Math.min(prevLen, len));
            prevLen = len;
        }
        return max;
    }
}
