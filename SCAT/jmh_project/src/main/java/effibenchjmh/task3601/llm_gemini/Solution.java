package effibenchjmh.task3601.llm_gemini;

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
        long num = k - 1;
        int sum = 0;
        int i = 0;
        while (num > 0) {
            if ((num & 1) == 1) {
                sum += operations[i];
            }
            num >>= 1;
            i++;
        }
        return sum;
    }


}
