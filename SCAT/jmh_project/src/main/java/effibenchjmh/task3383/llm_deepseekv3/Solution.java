package effibenchjmh.task3383.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int maximumEnergy(int[] energy, int k) {
        int n=energy.length;
        int maxEnergy = Integer.MIN_VALUE;

        for(int i=n-1;i>=0;i--){
            int sum = energy[i];
            if(i+k<n){
                sum += energy[i+k];
                energy[i] = sum;
            }
            if(sum > maxEnergy) {
                maxEnergy = sum;
            }
        }

        return maxEnergy;
    }
}
