package effibenchjmh.task3611.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public static int[] minBitwiseArray(List<Integer> nums) {
        int n = nums.size();
        int[] ans = new int[n];
        for(int j=0; j<n; j++){
            int x = nums.get(j);
            if (x == 1 || x == 2) {
                ans[j] = -1;
            } else {
                int L = 32 - Integer.numberOfLeadingZeros(x);
                if (x == (1 << L) - 1) {
                    // all bits set: flip highest bit
                    ans[j] = x - (1 << (L - 1));
                } else {
                    // find first 0 after the least significant 1-run
                    int r = Integer.numberOfTrailingZeros(~x);
                    if (r > 0) {
                        ans[j] = x - (1 << (r - 1));
                    } else {
                        ans[j] = x;
                    }
                }
            }
        }

        return ans;
    }
}
