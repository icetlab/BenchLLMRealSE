package effibenchjmh.task3243.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public long numberOfPowerfulInt(long start, long finish, int limit, String s) {
        long initStart = start;
        long x = Long.parseLong(s);
        int nOfSuffixDigits = s.length();
        long rang = 1;
        for (int i = 0; i < nOfSuffixDigits; i++) {
            rang *= 10;
        }

        // get proper start
        if (x >= start) {
            start = x;
        } else {
            long rem = start % rang;
            long prefix = start / rang;
            start = prefix * rang + x + (rem <= x ? 0 : rang);
        }

        long startPrefix = getResForStart(start, limit, rang);
        start = startPrefix * rang + x;

        // get proper finish
        if (x > finish) {
            return 0;
        }
        long finishPrefix = getResForFinish(finish, limit, rang);
        long currFinish = finishPrefix * rang;
        long finishRem = finish % rang;
        if (finishRem >= x) {
            finish = currFinish + x;
        } else {
            finish = currFinish + x < finish ? currFinish + x : currFinish + x - rang;
        }

        finishPrefix = getResForFinish(finish, limit, rang);
        finish = finishPrefix * rang + x;

        if (start > finish) return 0;
        if (start == finish && x == start) return 1;

        int shift = initStart > x ? 1 : 0;

        return getCalc(finish, limit, rang) - getCalc(start, limit, rang) + shift;
    }

    private long getCalc(long finish, int limit, long rang) {
        long prefix = finish / rang;
        if (prefix == 0) return 0;

        long count = 1;
        long place = 1;
        long base = limit + 1;

        while (prefix > 0) {
            count += (prefix % 10) * place;
            place *= base;
            prefix /= 10;
        }

        return count;
    }

    private long getResForFinish(long finish, int limit, long rang) {
        long prefix = finish / rang;
        if (prefix == 0) return 0;

        long power = 1;
        long temp = prefix;
        while (temp >= 10) {
            temp /= 10;
            power *= 10;
        }

        long res = 0;
        boolean overflow = false;
        while (power > 0) {
            int digit = (int) ((prefix / power) % 10);
            if (overflow) {
                res = res * 10 + limit;
            } else if (digit <= limit) {
                res = res * 10 + digit;
            } else {
                res = res * 10 + limit;
                overflow = true;
            }
            power /= 10;
        }
        return res;
    }

    private long getResForStart(long start, int limit, long rang) {
        long prefix = start / rang;
        long res = 0;
        long place = 1;
        int carry = 0;

        while (prefix > 0) {
            int digit = (int) (prefix % 10) + carry;
            if (digit > limit) {
                carry = 1;
                res = 0;
                place *= 10;
            } else {
                carry = 0;
                res += digit * place;
                place *= 10;
            }
            prefix /= 10;
        }
        if (carry == 1) {
            res += place;
        }
        return res;
    }
}

