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

        int half = cycleLen / 2;
        // Fill all distances less than half with cycleLen
        for (int len = 1; len < half; len++) {
            res[len - 1] = cycleLen;
        }

        // Handle the middle distance
        if (half - 1 >= 0) {
            if (cycleLen % 2 == 1) {
                res[half - 1] = cycleLen;
            } else {
                res[half - 1] = half;
            }
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

    // Main method to calculate number of pairs with a given distance
    public long[] countOfPairs(int n, int x, int y) {
        long[] res = new long[n];

        if (x > y) {
            int temp = x;
            x = y;
            y = temp;
        }

        int lineLen1 = x - 1;
        int lineLen2 = n - y;
        int cycleLen = y - x + 1;
        int finalLen = lineLen1 + lineLen2 + 2;

        long[] line1ans = lineAnswer(n, lineLen1);
        long[] line2ans = lineAnswer(n, lineLen2);
        long[] cycleAns = cycleAnswer(n, cycleLen);
        long[] l1c = lineAndCycle(n, lineLen1, cycleLen);
        long[] l2c = lineAndCycle(n, lineLen2, cycleLen);
        long[] l1l2 = twoLineAnswer(n, lineLen1, lineLen2, x, y);

        for (int i = 0; i < n; i++) {
            res[i] += line1ans[i];
            res[i] += line2ans[i];
            res[i] += cycleAns[i];
            res[i] += l1c[i];
            res[i] += l2c[i];
            res[i] += l1l2[i];
            res[i] *= 2; // Multiply since counting unordered pairs
        }

        return res;
    }
}