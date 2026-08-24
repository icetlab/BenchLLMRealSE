package effibenchjmh.task3500;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public long minimumCost(int m, int n, int[] horizontalCut, int[] verticalCut) {
        int i = 0, j = 0, hp = 1, vp = 1;
        long cost = 0;

        Arrays.sort(horizontalCut);
        Arrays.sort(verticalCut);

        reverse(horizontalCut);
        reverse(verticalCut);

        while (i < m - 1 && j < n - 1) {
            if (horizontalCut[i] > verticalCut[j]) {
                cost += (1L * vp) * horizontalCut[i++];
                hp++;
            } else {
                cost += (1L * hp) * verticalCut[j++];
                vp++;
            }
        }

        while (i < m - 1) {
            cost += (1L * vp) * horizontalCut[i++];
            hp++;
        }

        while (j < n - 1) {
            cost += (1L * hp) * verticalCut[j++];
            vp++;
        }

        return cost;
    }

    private void reverse(int[] arr) {
        int left = 0, right = arr.length - 1;
        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }
}
