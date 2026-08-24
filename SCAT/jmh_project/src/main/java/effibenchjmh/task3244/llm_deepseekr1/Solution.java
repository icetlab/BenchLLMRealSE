package effibenchjmh.task3244.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int minimumArrayLength(int[] nums) {
        int min = Integer.MAX_VALUE;
        for (int num : nums) {
            if (num < min) min = num;
        }

        int count = 0;
        for (int num : nums) {
            if (num == min) {
                count++;
            } else if (num % min != 0) {
                return 1;
            }
        }

        if (count == 1) {
            return 1;
        }
        return (count + 1) / 2;
    }
}
