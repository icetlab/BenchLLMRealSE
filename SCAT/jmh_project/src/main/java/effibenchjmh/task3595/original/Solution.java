package effibenchjmh.task3595.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public boolean isPossibleToRearrange(String s, String t, int k) {
        if(s.length()%k!=0 || s.length()!=t.length()){
            return false;
        }

        String[] s1 = new String[k];
        String[] t1 = new String[k];
        int sidx = 0;   
        int tidx =0 ;
        for(int i=0; i<k; i++){
            StringBuilder sb = new StringBuilder("");
            StringBuilder sb2 = new StringBuilder("");

            for(int j=0; j<s.length()/k; j++){
                sb.append(s.charAt(sidx++));
                sb2.append(t.charAt(tidx++));
            }
            s1[i]=  sb.toString();
            t1[i]=  sb2.toString();
        }

        Arrays.sort(s1);
        Arrays.sort(t1);

        for(int i=0; i<s1.length; i++){
            if(!s1[i].equals(t1[i])){
                return false;
            }
        }
        return true;
    }
}
