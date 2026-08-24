package effibenchjmh.task3555;

import java.util.*;
import java.util.stream.*;

//Java Code
class Solution {
    public int[] getFinalState(int[] nums, int k, int multiplier) {
        int n = nums.length;
        while(k-- != 0){
            int ans = nums[0], idx = 0;
            for(int i = 0; i < n; i++){
                if(nums[i] < ans){
                    ans = nums[i];
                    idx = i;
                }
                    
            }
            nums[idx] *= multiplier;
        }
        return nums;
    }
}
