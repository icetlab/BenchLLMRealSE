package effibenchjmh.task3696;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public long countSubstrings(String s) {
        int n = s.length();
        long ans = 0;
        long[] freq3 = new long[3];
        freq3[0] = 1;
        long[] freq9 = new long[9];
        freq9[0] = 1;
        long[][] freq7 = new long[6][7];
        freq7[0][0] = 1;
        int mod3 = 0, mod9 = 0, mod7 = 0;
        int[] inv7 = {1, 5, 4, 6, 2, 3};
        for (int j = 0; j < n; j++) {
            int d = s.charAt(j) - '0';
            mod3 = (mod3 + d) % 3;
            mod9 = (mod9 + d) % 9;
            mod7 = (mod7 * 10 + d) % 7;
            int k = j + 1, kMod = k % 6;
            char c = s.charAt(j);
            if (c == '0') {
            } else if (c == '1') {
                ans += (j + 1);
            } else if (c == '2') {
                ans += (j + 1);
            } else if (c == '5') {
                ans += (j + 1);
            } else if (c == '3') {
                ans += freq3[mod3];
            } else if (c == '6') {
                ans += freq3[mod3];
            } else if (c == '9') {
                ans += freq9[mod9];
            } else if (c == '4') {
                long add = 1;
                if (j >= 1) {
                    int prev = s.charAt(j - 1) - '0';
                    int two = prev * 10 + 4;
                    if (two % 4 == 0)
                        add += j;
                }
                ans += add;
            } else if (c == '8') {
                long add = 1;
                if (j >= 1) {
                    int prev = s.charAt(j - 1) - '0';
                    int two = prev * 10 + 8;
                    if (two % 8 == 0)
                        add += 1;
                }
                if (j >= 2) {
                    int d1 = s.charAt(j - 2) - '0';
                    int d2 = s.charAt(j - 1) - '0';
                    int three = d1 * 100 + d2 * 10 + 8;
                    if (three % 8 == 0)
                        add += (j - 1);
                }
                ans += add;
            } else if (c == '7') {
                for (int r = 0; r < 6; r++) {
                    int L0 = (kMod - r + 6) % 6;
                    int target = (mod7 * inv7[L0]) % 7;
                    ans += freq7[r][target];
                }
            }
            freq3[mod3]++;
            freq9[mod9]++;
            freq7[kMod][mod7]++;
        }
        return ans;
    }
}
