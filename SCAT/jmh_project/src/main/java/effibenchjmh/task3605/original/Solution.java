package effibenchjmh.task3605.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int[] minBitwiseArray(List<Integer> nums) {
        int[] arr = new int[nums.size()];
        for (int i=0; i<nums.size(); i++){
            for (int j=0; j<nums.get(i); j++){
                if ((j | (j+1)) == nums.get(i)){
                    arr[i]=j;
                    break;
                }
                if (arr[i]==0){
                    arr[i]=-1;
                } 
            }
        }
        return arr;
    }
}
