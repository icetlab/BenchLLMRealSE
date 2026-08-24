package effibenchjmh.task3194.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public static boolean ans(String s,char w){
        boolean re=true;
        List<Character> l=new ArrayList<>();
        for(char i:s.toCharArray()) l.add(i);
        if(!l.contains(w)) re=false;
        return re;
    }
    public List<Integer> findWordsContaining(String[] words, char x) {
        List<String> l=new ArrayList<>();
		List<Integer> l1=new ArrayList<>();
        for(String i:words){
            l.add(i);
        }
        for(int i=0;i<l.size();i++){
            boolean s=ans(l.get(i),x);
            if(s) l1.add(i);
        }
        return l1;


    }
}
