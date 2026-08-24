package effibenchjmh.task3244.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    public int minimumArrayLength(int[] nums) {
        HashMap<Integer,Integer> map=new HashMap<>();
        int min=Integer.MAX_VALUE;

        for(int i=0;i<nums.length;i++){
            map.put(nums[i],map.getOrDefault(nums[i],0)+1);
            min=Math.min(min,nums[i]);
        }   

        if(map.get(min)==1){
            return 1;
        }
        else if(map.get(min)>1){
            for(int i=0;i<nums.length;i++){
                if(nums[i]%min!=0){
                    return 1;
                }
            }
        }
        
        return (int)Math.ceil((double)map.get(min)/2);
    }
}
