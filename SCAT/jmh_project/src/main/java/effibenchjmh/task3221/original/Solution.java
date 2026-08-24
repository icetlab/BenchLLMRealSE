package effibenchjmh.task3221.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public List<Integer> findPeaks(int[] mountain) {
      Scanner sc=new Scanner(System.in);
      List<Integer>l=new ArrayList<>();
      for(int i=1;i<mountain.length-1;i++){
        
            if(mountain[i]>mountain[i-1] && mountain[i]>mountain[i+1])
            l.add(i);
        }
      return l;  
    }
}
