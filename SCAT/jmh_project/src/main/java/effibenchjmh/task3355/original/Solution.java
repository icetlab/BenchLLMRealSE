package effibenchjmh.task3355.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int minimumLevels(int[] possible) {
        int n=possible.length;
        int[] alice=new int[n];
        int[] bob=new int[n];
        alice[0]=possible[0]==1?1:-1;
        bob[n-1]=possible[n-1]==1?1:-1;
        for(int i=1;i<n;i++){
            if(possible[i]==0){alice[i]=alice[i-1]-1;}
            else{alice[i]=alice[i-1]+1;}
        }
        for(int i=n-2;i>=0;i--){
            if(possible[i]==0){bob[i]=bob[i+1]-1;}
            else{bob[i]=bob[i+1]+1;}
        }
        for(int i=0;i<n-1;i++){
            if(alice[i]>bob[i+1]){return i+1;}
        }
        return -1;
    }
}
