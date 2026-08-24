package effibenchjmh.task3562;

import java.util.*;
import java.util.stream.*;

class Solution {
    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();
        Interval[] arr = new Interval[n];
        for (int i = 0; i < n; i++) {
            List<Integer> cur = intervals.get(i);
            int l = cur.get(0), r = cur.get(1);
            long w = cur.get(2);
            arr[i] = new Interval(l, r, w, i);
        }
        Arrays.sort(arr, new Comparator<Interval>() {
            public int compare(Interval a, Interval b) {
                if(a.l != b.l) return a.l - b.l;
                if(a.r != b.r) return a.r - b.r;
                return a.orig - b.orig;
            }
        });
        int[] starts = new int[n];
        for (int i = 0; i < n; i++) {
            starts[i] = arr[i].l;
        }
        int[] next = new int[n];
        for (int i = 0; i < n; i++) {
            int lo = 0, hi = n;
            while (lo < hi) {
                int mid = (lo + hi) / 2;
                if (starts[mid] > arr[i].r) hi = mid;
                else lo = mid + 1;
            }
            next[i] = lo;
        }
        int CAP = 5;
        DPState[][] dp = new DPState[n + 1][CAP];
        for (int j = 0; j < CAP; j++) {
            dp[n][j] = new DPState(0, new ArrayList<Integer>());
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int cap = 0; cap < CAP; cap++) {
                DPState skip = dp[i + 1][cap];
                if (cap == 0) {
                    dp[i][cap] = skip;
                } else {
                    DPState pick = new DPState(arr[i].w + dp[next[i]][cap - 1].score, combineAndSort(arr[i].orig, dp[next[i]][cap - 1].sol));
                    dp[i][cap] = better(skip, pick);
                }
            }
        }
        List<Integer> solList = dp[0][4].sol;
        int[] res = new int[solList.size()];
        for (int i = 0; i < solList.size(); i++) {
            res[i] = solList.get(i);
        }
        return res;
    }
    private static List<Integer> combineAndSort(int num, List<Integer> list) {
        List<Integer> combined = new ArrayList<>(list.size() + 1);
        combined.add(num);
        combined.addAll(list);
        Collections.sort(combined);
        return combined;
    }
    private static DPState better(DPState a, DPState b) {
        if(a.score > b.score) return a;
        if(a.score < b.score) return b;
        return lexCompare(a.sol, b.sol) <= 0 ? a : b;
    }
    private static int lexCompare(List<Integer> a, List<Integer> b) {
        int len = Math.min(a.size(), b.size());
        for (int i = 0; i < len; i++) {
            if (!a.get(i).equals(b.get(i))) return a.get(i) - b.get(i);
        }
        return a.size() - b.size();
    }
}
class DPState {
    long score;
    List<Integer> sol;
    DPState(long score, List<Integer> sol) {
        this.score = score;
        this.sol = sol;
    }
}
class Interval {
    int l, r;
    long w;
    int orig;
    Interval(int l, int r, long w, int orig) {
        this.l = l;
        this.r = r;
        this.w = w;
        this.orig = orig;
    }
}
