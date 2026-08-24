package effibenchjmh.task3555.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
  public int[] getFinalState(int[] nums, int k, int multiplier) {
    int[] ans = new int[nums.length];
    // {num, index}
    Queue<int[]> minHeap = new PriorityQueue<>(
        (a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));

    for (int i = 0; i < nums.length; ++i)
      minHeap.offer(new int[] {nums[i], i});

    while (k-- > 0) {
      int[] top = minHeap.poll();
      top[0] *= multiplier;
      minHeap.offer(top);
    }

    for (int[] entry : minHeap) {
      ans[entry[1]] = entry[0];
    }

    return ans;
  }
}
