package effibenchjmh.task3446.original;

import java.util.*;
import java.util.stream.*;

import java.util.Hashtable;
class Solution {
    public int numberOfPairs(int[] nums1, int[] nums2, int k) {

        int count=0;

       Hashtable<Integer,Integer> ht = new Hashtable<>();

       for(int i=0;i<nums2.length;i++)
       {
            int product=nums2[i]*k;
            ht.put(i,product);
       }

       for(int nums:nums1)
       {
            for(Integer value:ht.values())
            {
                if(nums%value==0)
                {
                    count++;
                }
            }
       }
        return count;
    }
}
