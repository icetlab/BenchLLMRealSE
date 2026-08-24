package effibenchjmh.task3570.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int countOfSubstrings(String word, int k) {
        int minlen=5+k;
        int n=word.length();
        int substrs=n-minlen+1;
        int res=0;
        while(substrs>0){
            int l=0,r=0;
            while(l<=(n-minlen)){
                int temp=k;
                HashSet<Character> set=new HashSet<>(Set.of('a','e','i','o','u'));
                while(r-l<minlen){
                    char c=word.charAt(r);
                    if(set.contains(c)|| (c=='a' || c=='e' || c=='i' || c=='o' || c=='u') ){
                        if(!set.isEmpty())
                        set.remove(word.charAt(r));
                    }
                    else temp--;
                    r++;
                }
                if(set.isEmpty() && temp==0) res++;
                l++;
                r=l;
            }
            minlen++;
            substrs=n-minlen+1;
        }
        return res;
    }
}
