package effibenchjmh.task3289.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int earliestSecondToMarkIndices(int[] nums, int[] changeIndices) {
        if (nums.length == 1 && nums[0] == 0) {
            return 1;
        }

        for (int i = 0; i < changeIndices.length; i++) {
            changeIndices[i]--;
        }

        long total = 0;
        for (int i = 0; i < nums.length; i++) {
            total += nums[i];
        }
        total += nums.length;

        int low = 0, high = changeIndices.length;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (isPossible(nums, changeIndices, mid, total)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return isPossible(nums, changeIndices, low, total) ? low + 1 : -1;
    }

    private boolean isPossible(int[] nums, int[] changeIndices, int mid, long total) {
        if (mid >= changeIndices.length) {
            return false;
        }

        int[] firstIndices = new int[nums.length];
        java.util.Arrays.fill(firstIndices, -1);
        for (int i = 0; i <= mid; i++) {
            int idx = changeIndices[i];
            if (nums[idx] > 0 && firstIndices[idx] == -1) {
                firstIndices[idx] = i;
            }
        }

        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        int available = 0;
        for (int i = mid; i >= 0; i--) {
            int idx = changeIndices[i];
            if (firstIndices[idx] == i) {
                if (available > 0) {
                    priorityQueue.offer(new int[]{nums[idx], idx});
                    available--;
                } else if (!priorityQueue.isEmpty() && priorityQueue.peek()[0] < nums[idx]) {
                    priorityQueue.poll();
                    priorityQueue.offer(new int[]{nums[idx], idx});
                    available++;
                } else {
                    available++;
                }
            } else {
                available++;
            }
        }

        while (!priorityQueue.isEmpty()) {
            total -= (priorityQueue.poll()[0] + 1);
        }

        return total <= available;
    }
}
