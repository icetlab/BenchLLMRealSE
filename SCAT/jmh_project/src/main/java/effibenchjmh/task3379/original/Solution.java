package effibenchjmh.task3379.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int scoreOfString(String s) {
        char[] newarray=s.toCharArray();
        int[] holdval=new int[s.length()-1];
        for(int i=0;i<newarray.length-1;i++){
            //System.out.println(i+" "+(int)newarray[i]+" "+(int)newarray[i+1]);
            var val=(int)newarray[i]-(int)newarray[i+1];
            holdval[i]=Math.abs(val);
        }
        var sums=Arrays.stream(holdval).sum();
        return sums;
    }
}
