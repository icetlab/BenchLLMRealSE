package effibenchjmh.task3225.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

import java.util.HashMap;

class Solution {

    public boolean isGood(HashMap<Integer, Integer> map, int k) {
        for (int val : map.values())
            if (val > k)
                return false;
        return true;
    }

    public int maxSubarrayLength(int[] nums, int k) {
        int n = nums.length;
        HashMap<Integer, Integer> map = new HashMap<>(n * 2);
        int ans = 0;
        int left = 0;
        for (int right = 0; right < n; right++) {
            int num = nums[right];
            int freq = map.getOrDefault(num, 0) + 1;
            map.put(num, freq);

            while (freq > k) {
                int leftNum = nums[left++];
                int leftFreq = map.get(leftNum);
                if (leftFreq == 1) {
                    map.remove(leftNum);
                    leftFreq = 0;
                } else {
                    leftFreq--;
                    map.put(leftNum, leftFreq);
                }
                if (leftNum == num) {
                    freq = leftFreq;
                }
            }

            int windowLen = right - left + 1;
            if (windowLen > ans) {
                ans = windowLen;
            }
        }

        return ans;
    }
}
