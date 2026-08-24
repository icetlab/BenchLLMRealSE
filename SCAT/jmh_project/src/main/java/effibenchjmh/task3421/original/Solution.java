package effibenchjmh.task3421.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int countCompleteDayPairs(int[] hours) {
        HashMap<Integer, Integer> remainder_count = new HashMap<>();
        int count = 0;
        for(int h: hours){
            int remainder = h%24;
            if(remainder == 0){
                if(remainder_count.containsKey(0)){count += remainder_count.get(0);}
            }else{
                int cur = 24-remainder;
                if(remainder_count.containsKey(cur)){
                    count += remainder_count.get(cur);
                }
            }
            remainder_count.putIfAbsent(remainder, 0);
            remainder_count.put(remainder, remainder_count.get(remainder)+1);
        }
        return count;
    }
}
