package effibenchjmh.task3414.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int waysToReachStair(int k) {
        // Use a map to memoize results for (stair, jump, canGoDown) states
        java.util.Map<String, Integer> memo = new java.util.HashMap<>();
        return dfs(1, 0, true, k, memo);
    }
    
    private int dfs(int stair, int jump, boolean canGoDown, int k, java.util.Map<String, Integer> memo) {
        // If we've gone too far past k, no need to continue
        if (stair > k + 1) {
            return 0;
        }
        
        String key = stair + "," + jump + "," + canGoDown;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        
        int ways = (stair == k) ? 1 : 0;
        
        // Option 1: Go down (only if we came from up and stair > 0)
        if (canGoDown && stair > 0) {
            ways += dfs(stair - 1, jump, false, k, memo);
        }
        
        // Option 2: Go up
        ways += dfs(stair + (1 << jump), jump + 1, true, k, memo);
        
        memo.put(key, ways);
        return ways;
    }
}
