package effibenchjmh.task3220.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int countTestedDevices(int[] arr) {
        
        int positiveCount = 0;
        int ans = 0;
        for (int a : arr) {
            if (a > positiveCount) {
                ans++;
                positiveCount++;
            }
        }
        return ans;
    }
}
