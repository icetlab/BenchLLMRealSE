package effibenchjmh.task3595.llm_deepseekv3;

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

        int partLen = s.length() / k;
        long[] s1 = new long[k];
        long[] t1 = new long[k];
        int sidx = 0;
        int tidx = 0;
        for(int i=0; i<k; i++){
            long hashS = 0;
            long hashT = 0;
            for(int j=0; j<partLen; j++){
                hashS = hashS * 31 + s.charAt(sidx++);
                hashT = hashT * 31 + t.charAt(tidx++);
            }
            s1[i] = hashS;
            t1[i] = hashT;
        }

        Arrays.sort(s1);
        Arrays.sort(t1);

        for(int i=0; i<s1.length; i++){
            if(s1[i] != t1[i]){
                return false;
            }
        }
        return true;
    }
}
