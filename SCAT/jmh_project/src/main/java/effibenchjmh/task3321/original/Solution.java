package effibenchjmh.task3321.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    public String triangleType(int[] nums) {
            int count=0;
        for(int i=0;i<nums.length-1;i++){
            for(int j=i+1;j<nums.length;j++){
                if(nums[i]==nums[j]){
                    count++;
                    System.out.println(count);
                }
            }
        }
        if(count==0) {
//            return "scalene";
            Arrays.sort(nums);
            if(nums[0]+nums[1]<=nums[2])
                return "none";
            else return "scalene";
        }
        else if(count==1){
            Arrays.sort(nums);
        if(nums[0]+nums[1]<=nums[2])
            return "none";
        else return "isosceles";
        }
        else if(count==3)
            return "equilateral";
        return null;
    }
}
