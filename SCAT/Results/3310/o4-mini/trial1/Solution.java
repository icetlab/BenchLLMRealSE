public class Solution {

    // Combines a line and a cycle and counts pair distances
    public long[] lineAndCycle(int n, int lineLen, int cycleLen) {
        long[] res = new long[n];

        for (int d = 1; d <= lineLen; d++) {
            int mn = d + 1;
            int mx = d + (cycleLen / 2);
            res[mn - 1] += 2;
            res[mx - 1] -= 2;
        }

        int mn = cycleLen / 2 + 1;
        int mx = cycleLen / 2 + lineLen;

        int add = (cycleLen % 2 == 1 ? 2 : 1);
        res[mn - 1] += add;
        if (mx < n) res[mx] -= add;

        // Prefix sum to compute final values
        for (int i = 1; i < n; i++) {
            res[i] += res[i - 1];
        }

        // Account for distances of `1` from the line to the center
        for (int len = 1; len <= lineLen; len++) {
            res[len - 1]++;
        }

        return res;
    }

    // Computes distances in a single line/tree
    public long[] lineAnswer(int n, int lineLen) {
        long[] res = new long[n];

        for (int len = 1, add = lineLen - 1; len < lineLen; len++, add--) {
            res[len - 1] += add;
        }

        return res;
    }

    // Computes distances in a cycle
    public long[] cycleAnswer(int n, int cycleLen) {
        long[] res = new long[n];

        for (int len = 1; len < cycleLen / 2; len++) {
            res[len - 1] += cycleLen;
        }

        if (cycleLen % 2 == 1) {
            int len = cycleLen / 2;
            if (len - 1 >= 0) res[len - 1] += cycleLen;
        } else {
            int len = cycleLen / 2;
            if (len - 1 >= 0) res[len - 1] += (cycleLen / 2);
        }

        return res;
    }

    // Computes distances when two lines are connected via a central node or path
    public long[] twoLineAnswer(int n, int lineLen1, int lineLen2, int x, int y) {
        long[] res = new long[n];

        // Ensure lineLen1 is the shorter or equal line
        if (lineLen1 > lineLen2) {
            int temp = lineLen1;
            lineLen1 = lineLen2;
            lineLen2 = temp;
        }

        int plus = (x != y ? 1 : 0);

        for (int i = 0; i < lineLen1; i++) {
            int mn = i + plus + 2;
            int mx = i + plus + 1 + lineLen2;

            res[mn - 1]++;
            if (mx < n) res[mx]--;
        }

        // Prefix sum
        for (int i = 1; i < n; i++) {
            res[i] += res[i - 1];
        }

        return res;
    }

    // Helper to add diff for lineAndCycle without prefix sum and direct additions
    private void addLineAndCycleDiff(long[] diff, int n, int lineLen, int cycleLen) {
        for (int d = 1; d <= lineLen; d++) {
            int mn = d + 1;
            int mx = d + (cycleLen / 2);
            diff[mn - 1] += 2;
            if (mx - 1 < n) diff[mx - 1] -= 2;
        }
        int mn = cycleLen / 2 + 1;
        int mx = cycleLen / 2 + lineLen;
        int add = (cycleLen % 2 == 1 ? 2 : 1);
        diff[mn - 1] += add;
        if (mx < n) diff[mx] -= add;
    }

    // Helper to add diff for twoLineAnswer without prefix sum
    private void addTwoLineDiff(long[] diff, int n, int lineLen1, int lineLen2, int plus) {
        for (int i = 0; i < lineLen1; i++) {
            int mn = i + plus + 2;
            int mx = i + plus + 1 + lineLen2;
            diff[mn - 1]++;
            if (mx < n) diff[mx]--;
        }
    }

    // Main method to calculate number of pairs with a given distance
    public long[] countOfPairs(int n, int x, int y) {
        long[] res = new long[n];
        long[] diff = new long[n];

        if (x > y) {
            int temp = x;
            x = y;
            y = temp;
        }

        int lineLen1 = x - 1;
        int lineLen2 = n - y;
        int cycleLen = y - x + 1;

        // Direct contributions: lineAnswer for both lines
        for (int len = 1, add = lineLen1 - 1; len < lineLen1; len++, add--) {
            res[len - 1] += add;
        }
        for (int len = 1, add = lineLen2 - 1; len < lineLen2; len++, add--) {
            res[len - 1] += add;
        }

        // Direct contributions: cycleAnswer
        for (int len = 1; len < cycleLen / 2; len++) {
            res[len - 1] += cycleLen;
        }
        if (cycleLen % 2 == 1) {
            int len = cycleLen / 2;
            if (len - 1 >= 0) res[len - 1] += cycleLen;
        } else {
            int len = cycleLen / 2;
            if (len - 1 >= 0) res[len - 1] += (cycleLen / 2);
        }

        // Add diff contributions for lineAndCycle
        addLineAndCycleDiff(diff, n, lineLen1, cycleLen);
        addLineAndCycleDiff(diff, n, lineLen2, cycleLen);

        // Account for distances of `1` from the line to the center (direct)
        for (int len = 1; len <= lineLen1; len++) res[len - 1]++;
        for (int len = 1; len <= lineLen2; len++) res[len - 1]++;

        // Add diff contributions for twoLineAnswer
        addTwoLineDiff(diff, n, lineLen1, lineLen2, (x != y ? 1 : 0));

        // Prefix sum diff and merge
        for (int i = 1; i < n; i++) {
            diff[i] += diff[i - 1];
        }
        for (int i = 0; i < n; i++) {
            res[i] += diff[i];
            res[i] *= 2;
        }

        return res;
    }
}