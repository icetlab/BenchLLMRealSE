package effibenchjmh.task3685.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int countSubarrays(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int k = 3;
        int sum = 0;

        for (int i = 0; i <= nums.length - k; i++) {
            List<Integer> subarray = new ArrayList<>();
            for (int j = i; j < i + k; j++) {
                subarray.add(nums[j]);
            }
            result.add(subarray);
        }
        for(List s: result) {
            int n = (int) s.get(0) + (int)s.get(2);
            if(n * 2 ==(int)s.get(1)) {
                sum += 1;
            }
        }
        return sum;
    }
}
