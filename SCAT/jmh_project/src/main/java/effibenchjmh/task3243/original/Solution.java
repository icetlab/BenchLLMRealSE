package effibenchjmh.task3243.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public long numberOfPowerfulInt(long start, long finish, int limit, String s) {
        long initStart = start;
        long result = 0;
        long x = Long.valueOf(s);
        int nOfSuffixDigits = s.length();
        long rang = Long.valueOf("1" + "0".repeat(nOfSuffixDigits));

        // get proper start
        if (x >= start) {
            start = x;
        } else {           
            start = start % rang <= x ? start/rang * rang + x : start/rang * rang + x + rang;
        }

        String res = getResForStart(start, limit, rang);
        start = Long.valueOf(res + s);

        // get proper finish
        if (x > finish) {
            return 0;
        } else {
            res = getResForFinish(finish, limit, rang);
            long currFinish = Long.valueOf(res) * rang;
            if (finish % rang >= x) {
                finish = currFinish + x;
            } else {
                finish = currFinish + x < finish ? currFinish + x : currFinish + x - rang;  
            }
        }

        res = getResForFinish(finish, limit, rang);
        finish = Long.valueOf(res + s);

        if (start > finish) return 0;
        if (start == finish && x == start) return 1;

        int shift = initStart > x ? 1 : 0;

        return getCalc(finish, limit, rang) - getCalc(start, limit, rang) + shift;
    }

    private long getCalc(long finish, int limit, long rang) {

        if (finish / rang == 0) return 0;

        String str = String.valueOf(finish / rang);

        String d = str.substring(str.length() - 1, str.length());
        int digit = Integer.valueOf(d);

        long sum = digit + 1;
        long register = limit + 1;

        for (int i = str.length() - 2; i >= 0; i--) {
            d = str.substring(i, i + 1);
            digit = Integer.valueOf(d);

            sum = sum + digit * register;
      
            register = register * (limit + 1);
        }
    
        return sum;
    }

    private String getResForFinish(long finish, int limit, long rang) {
        String str = String.valueOf(finish / rang);
        String lim = String.valueOf(limit);
        String res = "";
        boolean flag = false;
        for (int i = 0; i < str.length(); i++) {
            String d = str.substring(i, i + 1);
            int digit = Integer.valueOf(d);
            if (digit <= limit) {
                res = flag == false ? res + d : res + lim;
            } else {
                res = res + lim;
                flag = true;
            }
        }

        return res;
    }

    private String getResForStart(long start, int limit, long rang) {
        String str = String.valueOf(start / rang);
        String lim = String.valueOf(limit);
        String res = "";
        int shift = 0;

        for (int i = str.length() - 1; i >= 0; i--) {
            String d = str.substring(i, i + 1);
            int digit = Integer.valueOf(d) + shift;
            if (digit > limit) {
                shift = 1;
                digit = 0;
                res = digit + "0".repeat(res.length());
            } else {
                shift = 0;
                res = digit + res;
            }
            
        }

        return shift == 1 ? "1" + res : res;
    }
}

