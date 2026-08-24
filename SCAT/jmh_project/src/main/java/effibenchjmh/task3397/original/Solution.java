package effibenchjmh.task3397.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int addedInteger(int[] nums1, int[] nums2) {
        int freq1[] = new int[1001];
        int freq2[] = new int[1001];

        for(int i = 0; i< nums1.length; i++){
            freq1[nums1[i]]++;
            freq2[nums2[i]]++;
        }

        int num1 = -1;
        int num2 = -1;

        for(int i = 0; i< 1001; i++){
            if(num1 == -1 && freq1[i] != 0) num1 = i;
            if(num2 == -1 && freq2[i] != 0) num2 = i;
        }
    return num2 - num1;
    }
}

/**
class Solution {
    public int addedInteger(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);

        return nums2[0] - nums1[0];
    }
}
 */
