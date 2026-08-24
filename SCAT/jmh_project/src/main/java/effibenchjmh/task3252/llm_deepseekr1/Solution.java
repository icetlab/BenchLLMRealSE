package effibenchjmh.task3252.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int incremovableSubarrayCount(int[] a) {
        int n = a.length;
        if (n == 0) return 0;
        
        // Precompute prefix increasing condition
        boolean[] preInc = new boolean[n];
        preInc[0] = true;
        for (int i = 1; i < n; i++) {
            preInc[i] = preInc[i-1] && (a[i-1] < a[i]);
        }
        
        // Find the smallest index L such that suffix a[L..n-1] is strictly increasing
        int L = n-1;
        for (int i = n-2; i >= 0; i--) {
            if (a[i] < a[i+1]) {
                L = i;
            } else {
                break;
            }
        }
        
        int count = 0;
        // Iterate over possible start i of removable subarray
        for (int i = 0; i < n; i++) {
            // prefix condition
            if (i > 0 && !preInc[i-1]) {
                continue;
            }
            
            // always include j = n-1 (remove suffix starting from i to end)
            count++;
            
            // Now consider j from i to n-2
            int startJ;
            if (i == 0) {
                startJ = Math.max(0, L-1);
            } else {
                int x = a[i-1];
                // binary search in suffix a[L..n-1] for first > x
                int lo = L, hi = n-1;
                int k = -1;
                while (lo <= hi) {
                    int mid = (lo + hi) / 2;
                    if (a[mid] > x) {
                        k = mid;
                        hi = mid - 1;
                    } else {
                        lo = mid + 1;
                    }
                }
                if (k == -1) {
                    startJ = n; // no valid j < n-1
                } else {
                    int jMin = k - 1;
                    startJ = Math.max(i, jMin);
                }
            }
            if (startJ <= n-2) {
                count += (n-2 - startJ + 1);
            }
        }
        return count;
    }
}
