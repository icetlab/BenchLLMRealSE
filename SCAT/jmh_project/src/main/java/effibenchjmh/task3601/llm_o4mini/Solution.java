package effibenchjmh.task3601.llm_o4mini;

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

    private int findit(long k, int[] operations) {
        int count = 0;
        while (k != 1) {
            long p = Long.highestOneBit(k - 1);
            int x = Long.numberOfTrailingZeros(p);
            if (operations[x] == 1) count++;
            k -= p;
        }
        return count;
    }


}
