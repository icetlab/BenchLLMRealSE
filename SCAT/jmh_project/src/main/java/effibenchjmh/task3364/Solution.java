package effibenchjmh.task3364;

import java.util.*;
import java.util.stream.*;

class Solution {
    
    public int minimumValueSum(int[] nums, int[] andValues) {
        return dfs(0, 0, ~0, andValues, nums, new HashMap<>());
    }
    
    int dfs(int i, int j, int currentValue, int[] andValues, int[] nums, Map<String, Integer> cache) {
        
        if(i == nums.length && j == andValues.length) {
            return 0;
        }
        
        if(i == nums.length || j == andValues.length) {
            return -1;
        }
        
        currentValue &= nums[i];
        
        if(currentValue < andValues[j]) {
            return -1;
        }
        
        String key = i + "-" + j + "-" + currentValue;
        
        if(cache.containsKey(key)) {
            return cache.get(key);
        }
        
        int continueTheArray = dfs(i + 1, j, currentValue, andValues, nums, cache);
        
        //can't break the subarray here, need to continue
        if(currentValue != andValues[j]) {
            cache.put(key, continueTheArray);
            return cache.get(key);
        }
        
        // break and evaluate
        int next = dfs(i + 1, j + 1, ~0, andValues, nums, cache);
            
        if(next == -1) {
            cache.put(key, continueTheArray);
            return cache.get(key);
        }

        if(continueTheArray == -1) {
            cache.put(key, nums[i] + next);
            return cache.get(key);
        }

        cache.put(key, Math.min(nums[i] + next, continueTheArray));
        return cache.get(key);
        
    }
}
