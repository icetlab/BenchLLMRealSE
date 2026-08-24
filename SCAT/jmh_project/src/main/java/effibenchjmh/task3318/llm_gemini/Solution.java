package effibenchjmh.task3318.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {

public int maxOperations(int[] nums) {
    final int n = nums.length;
    if (n < 2) {
      return 0;
    }
    Map<Long, Integer> mem = new HashMap<>();
    int res1 = maxOperations(nums, 0, n - 1, nums[0] + nums[1], mem);
    int res2 = maxOperations(nums, 0, n - 1, nums[n - 1] + nums[n - 2], mem);
    int res3 = maxOperations(nums, 0, n - 1, nums[0] + nums[n - 1], mem);
    return Math.max(res1, Math.max(res2, res3));
  }

  // Returns the maximum number of operations that can be performed for
  // nums[i..j], s.t. all operations have the same `score`.
  private int maxOperations(int[] nums, int i, int j, int score, Map<Long, Integer> mem) {
    if (i >= j) {
      return 0;
    }
    final long key = hash(i, j, score);
    if (mem.containsKey(key)) {
      return mem.get(key);
    }
    int res = 0;
    if (nums[i] + nums[i + 1] == score) {
      res = Math.max(res, 1 + maxOperations(nums, i + 2, j, score, mem));
    }
    if (nums[j] + nums[j - 1] == score) {
      res = Math.max(res, 1 + maxOperations(nums, i, j - 2, score, mem));
    }
    if (nums[i] + nums[j] == score) {
      res = Math.max(res, 1 + maxOperations(nums, i + 1, j - 1, score, mem));
    }
    mem.put(key, res);
    return res;
  }

  private long hash(int i, int j, int score) {
    return ((long) i << 22) | ((long) j << 11) | score;
  }
    
}
