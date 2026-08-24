package effibenchjmh.task3221.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public List<Integer> findPeaks(int[] mountain) {

      List<Integer> l = new ArrayList<>(Math.max(0, mountain.length - 2));
      int n = mountain.length;
      for(int i=1;i<n-1;i++){
        
            if(mountain[i]>mountain[i-1] && mountain[i]>mountain[i+1])
            l.add(i);
        }
      return l;  
    }
}
