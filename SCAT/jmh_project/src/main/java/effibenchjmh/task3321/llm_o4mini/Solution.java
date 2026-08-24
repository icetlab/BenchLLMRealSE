package effibenchjmh.task3321.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

import java.util.Arrays;

class Solution {
    public String triangleType(int[] nums) {
        Arrays.sort(nums);
        int a = nums[0], b = nums[1], c = nums[2];
        if (a + b <= c) return "none";
        if (a == c) return "equilateral";
        if (a == b || b == c) return "isosceles";
        return "scalene";
    }
}
