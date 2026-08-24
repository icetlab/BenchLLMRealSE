package effibenchjmh.task3226.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int[] numberGame(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for(int i : nums) list.add(i);
        Collections.sort(list);
        int[] ans = new int[nums.length];
        int c=nums.length;
        int i=0;
        int ptr=0;
        while(c!=0){
            c-=2;
            int firstSmall = list.get(ptr++);
            int secondSmall = list.get(ptr++);
            ans[i++] = secondSmall;
            ans[i++] = firstSmall;
        }
    return ans;
    }
}
