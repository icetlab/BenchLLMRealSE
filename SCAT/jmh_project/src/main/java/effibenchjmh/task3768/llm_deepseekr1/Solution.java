package effibenchjmh.task3768.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public boolean hasSameDigits(String s) {
        int n = s.length();
        if (n <= 2) {
            return n == 2 && s.charAt(0) == s.charAt(1);
        }

        int m = n - 2;
        int[] digits = new int[n];
        for (int i = 0; i < n; i++) {
            digits[i] = s.charAt(i) - '0';
        }

        int[][] c5 = {
            {1, 0, 0, 0, 0},
            {1, 1, 0, 0, 0},
            {1, 2, 1, 0, 0},
            {1, 3, 3, 1, 0},
            {1, 4, 1, 4, 1}
        };

        int first = 0;
        int second = 0;
        for (int j = 0; j <= m; j++) {
            int c2 = ((j & ~m) == 0) ? 1 : 0;

            int mm = m;
            int kk = j;
            int mod5 = 1;
            while (mm > 0 || kk > 0) {
                int mi = mm % 5;
                int ki = kk % 5;
                if (ki > mi) {
                    mod5 = 0;
                    break;
                }
                mod5 = (mod5 * c5[mi][ki]) % 5;
                mm /= 5;
                kk /= 5;
            }

            int coeff;
            if (mod5 == 0) {
                coeff = (c2 == 0) ? 0 : 5;
            } else {
                coeff = mod5;
                if ((coeff & 1) != c2) {
                    coeff += 5;
                }
            }

            first = (first + coeff * digits[j]) % 10;
            second = (second + coeff * digits[j + 1]) % 10;
        }

        return first == second;
        
    }
}
