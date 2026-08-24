package effibenchjmh.task3601;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public char kthCharacter(long k, int[] operations) {
        int i = (int) (Math.ceil(Math.log(k) / Math.log(2))) - 1;
        int count = 0;

        while (k > 1) {
            if (k > (1L << i)) {
                if (operations[i] == 1) {
                    count++;
                }
                k -= (1L << i);
            }
            i--;
        }

        return (char) ('a' + (count % 26));
    }
}
