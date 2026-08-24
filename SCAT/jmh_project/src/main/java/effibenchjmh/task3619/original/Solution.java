package effibenchjmh.task3619.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int maxIncreasingSubarrays(List<Integer> nums) {
        int n = nums.size();
        List<Integer> lengths = new ArrayList<>();
        

        int i = 0;
        while(i < nums.size()) {
            int start = i;
            i++;

            while(i < n && nums.get(i) > nums.get(i-1)) {
                i++;
            }
            System.out.println(i-start);

            lengths.add(i-start);
        }
        int max = lengths.get(0)/2;
        for(int j = 1; j < lengths.size(); j++) {
            max = Math.max(max,lengths.get(j)/2);
            max = Math.max(max, (Math.min(lengths.get(j-1), lengths.get(j))));
        }
        return max;
    }
}
