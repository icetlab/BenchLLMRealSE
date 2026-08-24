package effibenchjmh.task3685;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int countSubarrays(int[] nums) {
        int count = 0;
        for (int i = 0; i < nums.length - 2; i++) {
            int num1 = nums[i];
            int num2 = nums[i + 1];
            int num3 = nums[i + 2];
            
            if ((double) (num1 + num3) == (double) num2 / 2) {
                count++;
            }
        }
        return count;
    }
}
