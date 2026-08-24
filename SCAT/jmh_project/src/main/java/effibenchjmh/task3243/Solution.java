package effibenchjmh.task3243;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public long numberOfPowerfulInt(long start, long finish, int limit, String s) {
        long sVal = Long.parseLong(s);
        
        long mul = 1;
        
        while (mul <= sVal) {
            mul *= 10;
        }
        
        long result = solve(finish, sVal, mul, limit);
        result  -= solve(start - 1, sVal, mul, limit);
        
        return result;
    }
    
    private double convertBase (long val, long base) {
        
        double result = 0;
        
        List<Integer> results = new ArrayList<>();
        
        int mod;
        
        while (val != 0) {
            mod = (int) (val % base);
            results.add(mod);
            val /= base;
        }
        
        Collections.reverse(results);
        
        for (int num : results) {
            result *= 10;
            result += num;
        }
        
        return result;
    }
    
    private long solve(long val, long s, long mul, int limit) {
        
        long max = 1;
        
        while (max * mul <= val) {
            max *= 10;
        }
        
        long min = -1;
        
        long mid;
        
        double mVal;
        
        while (max - min > 1) {
            mid = (max + min) / 2;
            
            mVal = convertBase(mid, limit + 1) * mul + s;
            
            //System.out.println(max + " " + min + " " + mVal + " " + val);
            
            if (mVal <= val) {
                min = mid;
            } else {
                max = mid;
            }
        }
        
        //System.out.println(max);
        return max;
    }
}
