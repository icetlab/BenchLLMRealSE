package effibenchjmh.task3220.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int countTestedDevices(int[] arr) {
        
        int ans = 0;
        for (int i = 0; i < arr.length; ++i) {
            if (arr[i] > ans) {
                arr[i] -= ans;
                ++ans;
            } else {
                arr[i] = 0;
            }
        }
        return ans;
    }
}
