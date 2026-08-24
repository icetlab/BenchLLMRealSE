package effibenchjmh.task3298;

import java.util.*;
import java.util.stream.*;

class Solution {
    public int maxSelectedElements(int[] arr) {
        
        Arrays.sort(arr); //important
        
        /**
            dp[i][0] represents maximum number of consecutive elements starting
            from i'th element of array when 1 is not added to the i'th element
            
            dp[i][1] represents maximum number of consecutive elements starting
            from i'th element of array when 1 is added to the i'th element
        */
        
        int dp[][] = new int[arr.length][];
        
        for (int i = 0; i < dp.length; i++)
            dp[i] = new int[]{1, 1};
        
        for (int i = arr.length - 2; i >= 0; i--) {
            //there are 3 different cases for DP transition
            
            //current element + 1 = next element, example: 9, 10
            if (arr[i] + 1 == arr[i + 1]) {
                dp[i][0] = 1 + dp[i + 1][0];
                dp[i][1] = 1 + dp[i + 1][1];
            }
            
            
            //current element + 2 = next element, example: 8, 10
            if (arr[i] + 2 == arr[i + 1]) {
                dp[i][1] = 1 + dp[i + 1][0];
            }
            
            //current element = next element, example: 10, 10
            if (arr[i] == arr[i + 1]) {
                
                dp[i][0] = 1 + dp[i + 1][1];
                
                //pay attention to following dp transitions
                dp[i][0] = Math.max(dp[i][0], dp[i + 1][0]);
                dp[i][1] = Math.max(dp[i][1], dp[i + 1][1]); 
            }
        }
        
        //return the maximum dp[i][]
        int result = 0;
        for (int i = 0; i < dp.length; i++) {
            result = Math.max(result, dp[i][0]);
            result = Math.max(result, dp[i][1]);
        }
        return result;
    }
}
