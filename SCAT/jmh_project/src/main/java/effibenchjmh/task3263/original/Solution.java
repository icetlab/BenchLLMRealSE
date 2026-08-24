package effibenchjmh.task3263.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    public int minimumCost(int[] nums) {
        ArrayList<Integer> arr= new ArrayList<>();
        for(int i : nums){
            arr.add(i);
        }
        int first =arr.remove(0);
        Collections.sort(arr);
        int result = first+arr.get(0)+arr.get(1);
        return result;
    }
}
