package effibenchjmh.task3601.llm_deepseekv3;

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
        // Find the highest set bit position using bit operations
        int x = 63 - Long.numberOfLeadingZeros(k - 1);
        if(operations[x] == 1){
            return 1 + findit(k - (1L << x), operations);
        }
        else{
            return findit(k - (1L << x), operations);
        }
    }


}
