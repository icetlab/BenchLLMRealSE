package effibenchjmh.task3194.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public static boolean ans(String s,char w){
        return s.indexOf(w) >= 0;
    }
    public List<Integer> findWordsContaining(String[] words, char x) {
        List<String> l=new ArrayList<>();
		List<Integer> l1=new ArrayList<>();
        l = java.util.Arrays.asList(words);
        for(int i=0;i<l.size();i++){
            boolean s=ans(l.get(i),x);
            if(s) l1.add(i);
        }
        return l1;


    }
}
