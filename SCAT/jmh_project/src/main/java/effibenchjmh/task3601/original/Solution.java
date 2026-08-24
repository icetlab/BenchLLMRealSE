package effibenchjmh.task3601.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public char kthCharacter(long k, int[] operations) {
        
        int a = findit(k,operations);

        return (char)('a' + a%26);

        // return 

        // return 'a';
    }

    private int findit(long k, int[] operations){
        if(k == 1){
            return 0;
        }
        int x = 1;
        while((long)Math.pow(2,x) < k){
            x++;
        }
        x--;
        int a = 0;
        if(operations[x] == 1){
            k -= Math.pow(2,x);
            return 1 + findit(k,operations);
        }
        else{
            k -= Math.pow(2,x);
            return findit(k,operations);
        }
       
    }


}
