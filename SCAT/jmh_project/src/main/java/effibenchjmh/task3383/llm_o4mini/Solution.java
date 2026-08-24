package effibenchjmh.task3383.llm_o4mini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int maximumEnergy(int[] energy, int k) {
        int n=energy.length;
        int pre[]=new int[n];

        for(int i=n-1;i>=0;i--){
            pre[i]=energy[i];
            if(i+k<n){
                pre[i]+=pre[i+k];
            }
        }

        int max = pre[0];
        for (int i = 1; i < n; i++) {
            if (pre[i] > max) {
                max = pre[i];
            }
        }
        return max;
    }
}
