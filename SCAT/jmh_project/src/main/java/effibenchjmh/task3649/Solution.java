package effibenchjmh.task3649;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    private int ans;
    private void helper(List<Integer> list, int len, int x, int k, int result, boolean used[]){
        if(result >= ans) return;
        
        if(len == 0){
            ans = Math.min(ans, result);
            return;
        }

        int size = list.size();
        for(int i = 0; i < size; i++){
            if(used[i]) continue;
            int t = (int)(Math.ceil(list.get(i) * 1.0 / x));
            used[i] = true;
            helper(list, len - 1, x + k, k, result + t, used);
            used[i] = false;
        }
    }

    public int findMinimumTime(List<Integer> strength, int k) {
        int len = strength.size();
        ans = Integer.MAX_VALUE;
        helper(strength, len, 1, k, 0, new boolean[len]);
        return ans;
    }
}
