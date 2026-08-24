package effibenchjmh.task3463.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int numberOfAlternatingGroups(int[] colors) {
        int start=0, end=0, count=0;
        HashMap<Integer, Integer> map = new HashMap<>();
        while(start<colors.length){
            int color = colors[end%colors.length];
            map.put(color, map.getOrDefault(color, 0)+1);
            if(end-start+1==3){
                int startColor = colors[start];
                if(map.size()==2 && startColor==color){
                    count++;
                }
                map.put(startColor, map.get(startColor)-1);
                if(map.get(startColor)==0){
                    map.remove(startColor);
                }
                start++;
            }
            end++;
        }

        return count;
    }
}
