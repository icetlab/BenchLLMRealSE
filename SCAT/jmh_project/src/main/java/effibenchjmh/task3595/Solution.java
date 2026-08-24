package effibenchjmh.task3595;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public boolean isPossibleToRearrange(String s, String t, int k) {
        List<String> l1 = new ArrayList<>();
        List<String> l2 = new ArrayList<>();
        int n = s.length()/k;
        for(int i=0;i<s.length();i=i+n){
            l1.add(s.substring(i,i+n));
            l2.add(t.substring(i,i+n));
        }
        Collections.sort(l1);
        Collections.sort(l2);
        if(l1.equals(l2)){
            return true;
        }
        return false;
    }
}
