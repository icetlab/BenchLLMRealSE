package effibenchjmh.task3243.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public long numberOfPowerfulInt(long start, long finish, int limit, String s) {
        long x = Long.parseLong(s);
        long rang = 1L;
        for (int i = 0; i < s.length(); i++) {
            rang *= 10;
        }

        // Find first powerful number's prefix >= start's prefix candidate
        long startCand = (start / rang) * rang + x;
        if (startCand < start) {
            startCand += rang;
        }
        long startPrefix = getResForStart(startCand / rang, limit);

        // Find last powerful number's prefix <= finish's prefix candidate
        if (finish < x) return 0;
        long finishCand = (finish / rang) * rang + x;
        if (finishCand > finish) {
            finishCand -= rang;
        }
        if (finishCand < 0) return 0;
        long finishPrefix = getResForFinish(finishCand / rang, limit);

        if (startPrefix > finishPrefix) {
            return 0;
        }

        long countFinish = getCalc(finishPrefix, limit);
        long countStart = getCalc(startPrefix, limit);

        return countFinish - countStart + 1;
    }

    // Counts number of valid prefixes from 0 to prefix (inclusive)
    private long getCalc(long prefix, int limit) {
        if (prefix < 0) return 0;
        if (prefix == 0) return 1;
        long count = 0;
        long powerOfLimitPlus1 = 1;
        long p = prefix;
        while (p > 0) {
            count += (p % 10) * powerOfLimitPlus1;
            p /= 10;
            powerOfLimitPlus1 *= (limit + 1);
        }
        return count + 1;
    }

    // Finds the largest number <= prefix with all digits <= limit
    private long getResForFinish(long prefix, int limit) {
        long p10 = 1;
        while (p10 <= prefix / 10) {
            p10 *= 10;
        }

        long res = 0;
        boolean flag = false;
        while (p10 > 0) {
            long digit = prefix / p10;
            if (flag) {
                res = res * 10 + limit;
            } else {
                if (digit > limit) {
                    res = res * 10 + limit;
                    flag = true;
                } else {
                    res = res * 10 + digit;
                }
            }
            prefix %= p10;
            p10 /= 10;
        }
        return res;
    }

    // Finds the smallest number >= prefix with all digits <= limit
    private long getResForStart(long prefix, int limit) {
        String sPrefix = Long.toString(prefix);
        int n = sPrefix.length();
        char[] res = sPrefix.toCharArray();
        
        int i = 0;
        while (i < n && (res[i] - '0') <= limit) {
            i++;
        }

        if (i == n) { // All digits are valid
            return prefix;
        }

        // Found a digit > limit at index i.
        // Backtrack to find the first digit from the right that we can increment.
        while (i > 0 && (res[i-1] - '0') == limit) {
            i--;
        }

        if (i == 0) { // All prefixes are like 44...45... with limit=4, need to add a digit
            char[] newRes = new char[n + 1];
            newRes[0] = '1';
            java.util.Arrays.fill(newRes, 1, newRes.length, '0');
            return Long.parseLong(new String(newRes));
        }

        // Increment digit at i-1
        res[i-1]++;
        
        // Set all subsequent digits to '0'
        java.util.Arrays.fill(res, i, n, '0');
        
        return Long.parseLong(new String(res));
    }
}

