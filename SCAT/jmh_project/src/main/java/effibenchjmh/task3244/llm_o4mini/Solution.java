package effibenchjmh.task3244.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int minimumArrayLength(int[] nums) {
        int min = Integer.MAX_VALUE;
        int countMin = 0;
        for (int num : nums) {
            if (num < min) {
                min = num;
                countMin = 1;
            } else if (num == min) {
                countMin++;
            }
        }
        if (countMin == 1) {
            return 1;
        }
        for (int num : nums) {
            if (num % min != 0) {
                return 1;
            }
        }
        return (countMin + 1) / 2;
    }
}
