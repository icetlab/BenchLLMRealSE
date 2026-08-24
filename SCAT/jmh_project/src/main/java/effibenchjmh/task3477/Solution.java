package effibenchjmh.task3477;

import java.util.*;
import java.util.stream.*;

class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        int i = 0, flips = 0;
        
        while (i < n) {
            nums[i] = (flips % 2 == 1) ? (nums[i] == 0 ? 1 : 0) : nums[i];
            
            if (nums[i] == 0) {
                flips++;
            }
            
            i++;
        }
        
        return flips;
    }
}
