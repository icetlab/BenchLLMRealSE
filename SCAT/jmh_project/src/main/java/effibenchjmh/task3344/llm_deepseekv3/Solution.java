package effibenchjmh.task3344.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {

    public int minimumDistance(int[][] points) {
        int n = points.length;
        if (n <= 1) return 0;

        // Precompute transformed coordinates
        int[] a = new int[n];
        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = points[i][0] + points[i][1];
            b[i] = points[i][0] - points[i][1];
        }

        // Find the two smallest and two largest values with their indices
        int[] minAIdx = new int[2], maxAIdx = new int[2];
        int[] minBIdx = new int[2], maxBIdx = new int[2];
        findTwoExtremes(a, minAIdx, maxAIdx);
        findTwoExtremes(b, minBIdx, maxBIdx);

        int res = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            // Compute min/max excluding index i
            int curMinA = (minAIdx[0] == i) ? a[minAIdx[1]] : a[minAIdx[0]];
            int curMaxA = (maxAIdx[0] == i) ? a[maxAIdx[1]] : a[maxAIdx[0]];
            int curMinB = (minBIdx[0] == i) ? b[minBIdx[1]] : b[minBIdx[0]];
            int curMaxB = (maxBIdx[0] == i) ? b[maxBIdx[1]] : b[maxBIdx[0]];

            int dist = Math.max(curMaxA - curMinA, curMaxB - curMinB);
            res = Math.min(res, dist);
        }
        return res;
    }

    private void findTwoExtremes(int[] arr, int[] minIdx, int[] maxIdx) {
        // Initialize with first two elements
        minIdx[0] = 0;
        minIdx[1] = 1;
        maxIdx[0] = 0;
        maxIdx[1] = 1;
        if (arr[1] < arr[0]) {
            minIdx[0] = 1;
            minIdx[1] = 0;
        } else {
            maxIdx[0] = 1;
            maxIdx[1] = 0;
        }
        for (int i = 2; i < arr.length; i++) {
            if (arr[i] < arr[minIdx[0]]) {
                minIdx[1] = minIdx[0];
                minIdx[0] = i;
            } else if (arr[i] < arr[minIdx[1]]) {
                minIdx[1] = i;
            }
            if (arr[i] > arr[maxIdx[0]]) {
                maxIdx[1] = maxIdx[0];
                maxIdx[0] = i;
            } else if (arr[i] > arr[maxIdx[1]]) {
                maxIdx[1] = i;
            }
        }
    }
}
