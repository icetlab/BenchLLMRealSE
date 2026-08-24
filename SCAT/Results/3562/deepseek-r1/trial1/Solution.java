import java.util.*;

class Solution {
    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();
        // Store start, end, weight, and original index
        int[] start = new int[n];
        int[] end = new int[n];
        int[] weight = new int[n];
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) {
            List<Integer> interval = intervals.get(i);
            start[i] = interval.get(0);
            end[i] = interval.get(1);
            weight[i] = interval.get(2);
            indices[i] = i;
        }
        // Sort by start, then by original index to maintain deterministic order
        Arrays.sort(indices, (a, b) -> {
            if (start[a] != start[b]) return Integer.compare(start[a], start[b]);
            return Integer.compare(a, b);
        });
        // Create sorted arrays
        int[] sStart = new int[n];
        int[] sEnd = new int[n];
        int[] sWeight = new int[n];
        int[] sOrigIdx = new int[n];
        for (int i = 0; i < n; i++) {
            int idx = indices[i];
            sStart[i] = start[idx];
            sEnd[i] = end[idx];
            sWeight[i] = weight[idx];
            sOrigIdx[i] = idx;
        }
        // Precompute next[i] = first index j > i such that sStart[j] > sEnd[i], or n if none
        int[] next = new int[n];
        for (int i = 0; i < n; i++) {
            next[i] = binarySearch(sStart, sEnd[i], i + 1);
        }
        // Dynamic programming arrays
        long[][] dpScore = new long[n + 1][5];
        List<Integer>[][] dpList = new ArrayList[n + 1][5];
        for (int k = 0; k <= 4; k++) {
            dpScore[n][k] = 0;
            dpList[n][k] = new ArrayList<>();
        }
        for (int i = n - 1; i >= 0; i--) {
            // k = 0
            dpScore[i][0] = 0;
            dpList[i][0] = new ArrayList<>();
            for (int k = 1; k <= 4; k++) {
                // Option 1: skip interval i
                long skipScore = dpScore[i + 1][k];
                List<Integer> skipList = dpList[i + 1][k];
                // Option 2: take interval i
                int nxt = next[i];
                long takeScore = sWeight[i] + dpScore[nxt][k - 1];
                List<Integer> takeList = new ArrayList<>(dpList[nxt][k - 1]);
                takeList.add(sOrigIdx[i]);
                Collections.sort(takeList);
                if (takeScore > skipScore) {
                    dpScore[i][k] = takeScore;
                    dpList[i][k] = takeList;
                } else if (takeScore < skipScore) {
                    dpScore[i][k] = skipScore;
                    dpList[i][k] = skipList;
                } else {
                    // Tie: choose lexicographically smaller list
                    List<Integer> chosen = lexiSmallerList(takeList, skipList);
                    dpScore[i][k] = takeScore;
                    dpList[i][k] = chosen;
                }
            }
        }
        List<Integer> resultList = dpList[0][4];
        int[] ans = new int[resultList.size()];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = resultList.get(i);
        }
        return ans;
    }

    // Binary search for first index >= lo such that sStart[idx] > val
    private int binarySearch(int[] sStart, int val, int lo) {
        int hi = sStart.length - 1;
        int ans = sStart.length;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (sStart[mid] > val) {
                ans = mid;
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return ans;
    }

    private List<Integer> lexiSmallerList(List<Integer> list1, List<Integer> list2) {
        int n1 = list1.size();
        int n2 = list2.size();
        int minLength = Math.min(n1, n2);
        for (int i = 0; i < minLength; i++) {
            int val1 = list1.get(i);
            int val2 = list2.get(i);
            if (val1 < val2) return list1;
            if (val1 > val2) return list2;
        }
        return n1 <= n2 ? list1 : list2;
    }
}