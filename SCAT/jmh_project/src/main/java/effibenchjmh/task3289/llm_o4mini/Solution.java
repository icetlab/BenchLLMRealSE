package effibenchjmh.task3289.llm_o4mini;

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

        changeIndices = Arrays.stream(changeIndices).map(x -> x - 1).toArray();

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

        int n = nums.length;
        int[] firstOcc = new int[n];
        Arrays.fill(firstOcc, -1);
        for (int i = 0; i <= mid; i++) {
            int idx = changeIndices[i];
            if (nums[idx] > 0 && firstOcc[idx] == -1) {
                firstOcc[idx] = i;
            }
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int available = 0;
        for (int i = mid; i >= 0; i--) {
            int idx = changeIndices[i];
            if (firstOcc[idx] == i) {
                if (available > 0) {
                    pq.offer(nums[idx]);
                    available--;
                } else if (!pq.isEmpty() && pq.peek() < nums[idx]) {
                    pq.poll();
                    pq.offer(nums[idx]);
                    available++;
                } else {
                    available++;
                }
            } else {
                available++;
            }
        }

        while (!pq.isEmpty()) {
            total -= (pq.poll() + 1);
        }

        return total <= available;
    }
}
