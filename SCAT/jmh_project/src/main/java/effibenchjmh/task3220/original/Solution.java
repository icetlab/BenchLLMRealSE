package effibenchjmh.task3220.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int countTestedDevices(int[] arr) {
        
        int prev = 0;
        int curr = 0;
        int ans = 0;
        for (int i=0;i<arr.length;++i) {
            curr = arr[i]; 
            arr[i] = Math.max(0, arr[i]-prev);
            if (arr[i] <= 0) continue;
            prev += curr > 0 ? 1 : 0;
            ans += 1;
        }
        for (int i=0;i<arr.length;++i) {
            System.out.print(arr[i]+" ");
        }
        return ans;
    }
}
