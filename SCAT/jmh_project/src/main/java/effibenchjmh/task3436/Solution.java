package effibenchjmh.task3436;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int minimumDifference(int[] nums, int k) {
        Set<Integer> s = new HashSet<>() ; 

        List<HashSet<Integer>> dp = new ArrayList<>() ; 

        for(int i=0;i<nums.length;i++) dp.add(new HashSet<>()) ; 

        s.add(nums[0]) ; 
        dp.get(0).add(nums[0]) ; 

        for(int i=1;i<nums.length;i++){
            s.add(nums[i]) ; 
            dp.get(i).add(nums[i]) ; 

            for(int x : dp.get(i-1)){
                int temp = x | nums[i] ; 
                s.add(temp) ; 
                dp.get(i).add(temp) ; 
            }
        }

        int ans = Integer.MAX_VALUE ; 

        for(int i : s){
            ans = Math.min(ans , Math.abs(k-i)) ; 
        }

        return ans ; 
    }
}
