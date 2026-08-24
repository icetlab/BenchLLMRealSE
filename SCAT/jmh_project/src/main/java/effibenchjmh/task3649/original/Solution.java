package effibenchjmh.task3649.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int min =Integer.MAX_VALUE;
    public int breakLocks(List<Integer> list , int k){
        int time =0;
        int  x =1;
        for(int i =0;i<list.size();i++){
            time = time +(int)Math.ceil((double)list.get(i) / x);
            x = x+k;
        }
        return time ;
    }
    public void premutations(List<Integer> list, List<Integer> strength, boolean visited[],int k){
        if(list.size()==strength.size()){
            int time = breakLocks(list,k);
            min = Math.min(min,time);
            return;
        }
        for(int i =0;i<strength.size();i++){
            if(!visited[i]){
            visited[i]=true;
            list.add(strength.get(i));
            premutations(list,strength,visited,k);
            list.remove(list.size()-1);
            visited[i]=false;
            }
        }

    }
    public int findMinimumTime(List<Integer> strength, int k) {
        int n = strength.size();
        boolean visited[]= new boolean[n];
        List<Integer> list = new ArrayList<>();
        premutations(list,strength,visited,k);

      return min;
        
    }
}
