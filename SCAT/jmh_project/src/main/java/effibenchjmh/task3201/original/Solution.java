package effibenchjmh.task3201.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

// 1..limit -- n - limit, limit

class Solution {

    private long cnt(int n, int l) {
        if (l > n) l = n;
        if (n > l * 2) return 0;
    //    System.out.println(n + " " + l);
        int b = n - l;
        return l - b + 1;
    }

    public long distributeCandies(int n, int limit) {
        long res = 0;
        for (int i = 0; i <= limit; i++) {
            res += cnt(n - i, limit);
          //  System.out.println(i + " " + res);
        }
        return res;
    }
}
