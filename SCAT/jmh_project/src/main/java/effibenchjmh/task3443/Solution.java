package effibenchjmh.task3443;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    
    Map<Integer, Integer> dp = new HashMap<>();
    
    public int maxTotalReward(int[] arr) {        
        arr = removeDups(arr);
        return arr[arr.length-1] + rec(arr, arr[arr.length-1] - 1);
    }
    
    private int rec(int[] arr, int lim) {
        if (lim == 0) {
            return 0;
        }
        if (dp.get(lim) != null) {
            return dp.get(lim);
        }
        int ind = find(arr, lim);
        if (ind == -1) {
            return lim;
        }
        int res = 0;
        for (int i = 0; i < ind; i++) {
            if (arr[i] * 2 - 1 <= res) {
                break;
            }
            res = Math.max(res, arr[i] + rec(arr, Math.min(lim - arr[i], arr[i] - 1)));
        }
        dp.put(lim, res);
        return res;
    }
    
    private int find(int[] arr, int x) {
        int lo = 0;
        int hi = arr.length - 1;
        int res = -1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (arr[mid] == x) {
                return -1;
            } else if (arr[mid] < x) {
                lo = mid + 1;
            } else {
                res = mid;
                hi = mid - 1;
            }
        }
        return res;
    }
    
    private int[] removeDups(int[] arr) {
        Set<Integer> set = new HashSet<>();
        for (int num: arr) {
            set.add(num);
        }
        int[] res = new int[set.size()];
        int i =0;
        for (int num: set) {
            res[i++] = num;
        }
        Arrays.sort(res);
        return res;
    }
}
