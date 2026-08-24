package effibenchjmh.task3197.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int maximumStrongPairXor(int[] A) {
        int res = 0;
        int maxVal = 0;
        for (int a : A) if (a > maxVal) maxVal = a;
        int size = maxVal + 1;
        
        for (int i = 20; i >= 0; i--) {
            res <<= 1;
            int[] minVal = new int[size];
            int[] maxValArr = new int[size];
            boolean[] seen = new boolean[size];
            
            for (int a : A) {
                int p = a >> i;
                if (!seen[p]) {
                    seen[p] = true;
                    minVal[p] = a;
                    maxValArr[p] = a;
                } else {
                    if (a < minVal[p]) minVal[p] = a;
                    if (a > maxValArr[p]) maxValArr[p] = a;
                }
            }
            
            for (int x = 0; x < size; x++) {
                if (!seen[x]) continue;
                int y = res ^ 1 ^ x;
                if (x >= y && y < size && seen[y] && minVal[x] <= maxValArr[y] * 2) {
                    res |= 1;
                    break;
                }
            }
        }
        return res;
    }
}
