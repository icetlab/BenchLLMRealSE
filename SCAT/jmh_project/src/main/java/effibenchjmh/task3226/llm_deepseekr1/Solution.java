package effibenchjmh.task3226.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int[] numberGame(int[] nums) {
        int[] result = nums.clone();
        java.util.Arrays.sort(result);
        for (int i = 0; i < result.length; i += 2) {
            int temp = result[i];
            result[i] = result[i + 1];
            result[i + 1] = temp;
        }
        return result;
    }
}
