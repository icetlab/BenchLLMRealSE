package effibenchjmh.task3747.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
   public int maxAdjacentDistance(int[] nums) {
		 int max = 0;
		 for(int i=0;i<nums.length;i++) {
			 int j = (i + 1) % nums.length;
			 int r = Math.abs(nums[i]-nums[j]);
			 if (r > max) max = r;
		 }
		 return max;
	    }
}
