class Solution {
    DpAns[][] memo;

    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();
        memo = new DpAns[n + 1][5];

        List<Pair> dintervals = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            dintervals.add(new Pair(i, intervals.get(i)));
        }

        Collections.sort(dintervals);

        DpAns result = solve(dintervals, 0, 4);

        int[] ans = new int[result.list.size()];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = result.list.get(i);
        }
        return ans;
    }

    public DpAns solve(List<Pair> intervals, int i, int k) {
        if (k == 0 || i >= intervals.size()) {
            return new DpAns(0, Collections.emptyList());
        }

        if (memo[i][k] != null) {
            return memo[i][k];
        }

        // Option 1: Skip interval i
        DpAns ans1 = solve(intervals, i + 1, k);

        // Option 2: Take interval i
        int nextIdx = findNextIndex(intervals, intervals.get(i).interval.get(1), i + 1, intervals.size() - 1);
        DpAns ans2 = solve(intervals, nextIdx, k - 1);
        long scoreTake = intervals.get(i).interval.get(2) + ans2.score;

        DpAns ans;
        if (scoreTake < ans1.score) {
            ans = ans1;
        } else {
            List<Integer> listTake = new ArrayList<>(ans2.list);
            listTake.add(intervals.get(i).idx);
            Collections.sort(listTake);

            if (scoreTake > ans1.score) {
                ans = new DpAns(scoreTake, listTake);
            } else { // Tie in score, decide by lexicographically smaller index list
                if (lexiSmallerList(ans1.list, listTake) == listTake) {
                    ans = new DpAns(scoreTake, listTake);
                } else {
                    ans = ans1;
                }
            }
        }

        memo[i][k] = ans;
        return ans;
    }

    
    public int findNextIndex(List<Pair> intervals, int val, int l, int r) {
        int ans = r + 1; 
        while (l <= r) {
            int mid = (l + r) / 2;
            int temp = intervals.get(mid).interval.get(0); 
            if (temp > val) {
                ans = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return ans;
    }

    public List<Integer> lexiSmallerList(List<Integer> list1, List<Integer> list2) {
        int n1 = list1.size();
        int n2 = list2.size();
        int minLength = Math.min(n1, n2);
        for (int i = 0; i < minLength; i++) {
            int val1 = list1.get(i);
            int val2 = list2.get(i);
            if (val1 < val2) {
                return list1;
            } else if (val1 > val2) {
                return list2;
            }
        }
        return n1 <= n2 ? list1 : list2;
    }
}


class Pair implements Comparable<Pair> {
    int idx; 
    List<Integer> interval;

    Pair(int idx, List<Integer> interval) {
        this.idx = idx;
        this.interval = interval;
    }
    public int compareTo(Pair p) {
        return Integer.compare(this.interval.get(0), p.interval.get(0));
    }
}

class DpAns {
    long score;
    List<Integer> list;

    DpAns(long score, List<Integer> list) {
        this.score = score;
        this.list = list;
    }
}