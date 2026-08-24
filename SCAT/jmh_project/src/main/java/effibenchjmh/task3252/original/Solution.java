package effibenchjmh.task3252.original;

import java.util.*;
import java.util.stream.*;

class Solution {
        public static  boolean check(List<Integer> al){
                for(int p=0;p<al.size()-1;p++){
                    if(al.get(p)>=al.get(p+1)){
                        return false;
                    }
                }
                return true;
        }
        public int incremovableSubarrayCount(int[] a) {
        int n=a.length;
        int count=0;
        for(int i=0;i<n;i++){
            for(int j=i;j<n;j++){
                ArrayList <Integer> al=new ArrayList<>();
                int k=0,l=j+1;
                while(k<i){
                    al.add(a[k]);
                    k++;
                }
                while(l<n){
                    al.add(a[l]);
                    l++;
                }
                if(check(al)){
                    count++;
                }      
            }
        }
        return count;
    }
}
