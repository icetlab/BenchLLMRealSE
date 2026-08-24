package effibenchjmh.task3579.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int maxGoodNumber(int[] nums) {
        String s1 = Integer.toBinaryString(nums[0]);
        String s2 = Integer.toBinaryString(nums[1]);
        String s3 = Integer.toBinaryString(nums[2]);
        
        String[] candidates = {
            s1 + s2 + s3,
            s1 + s3 + s2,
            s2 + s1 + s3,
            s2 + s3 + s1,
            s3 + s1 + s2,
            s3 + s2 + s1
        };
        
        String best = candidates[0];
        for (int i = 1; i < candidates.length; i++) {
            if (candidates[i].length() > best.length() || 
                (candidates[i].length() == best.length() && candidates[i].compareTo(best) > 0)) {
                best = candidates[i];
            }
        }
        
        return Integer.parseInt(best, 2);
    }
}
