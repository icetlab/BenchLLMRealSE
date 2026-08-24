package effibenchjmh.task3310.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

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

    // Main method to calculate number of pairs with a given distance
    public long[] countOfPairs(int n, int x, int y) {
        long[] diff = new long[n];

        if (x > y) {
            int temp = x;
            x = y;
            y = temp;
        }

        int lineLen1 = x - 1;
        int lineLen2 = n - y;
        int cycleLen = y - x + 1;

        addLineAnswer(diff, lineLen1);
        addLineAnswer(diff, lineLen2);
        addCycleAnswer(diff, n, cycleLen);
        addLineAndCycle(diff, n, lineLen1, cycleLen);
        addLineAndCycle(diff, n, lineLen2, cycleLen);
        addTwoLineAnswer(diff, n, lineLen1, lineLen2, x, y);

        long cur = 0;
        for (int i = 0; i < n; i++) {
            cur += diff[i];
            diff[i] = cur * 2;
        }
        return diff;
    }

    private void addLineAnswer(long[] diff, int lineLen) {
        if (lineLen < 2) {
            return;
        }
        diff[0] += lineLen - 1;
        for (int i = 1; i < lineLen; i++) {
            diff[i]--;
        }
    }

    private void addCycleAnswer(long[] diff, int n, int cycleLen) {
        if (cycleLen < 2) {
            return;
        }
        if ((cycleLen & 1) == 0) {
            int half = cycleLen / 2;
            diff[0] += cycleLen;
            diff[half - 1] -= half;
            if (half < n) {
                diff[half] -= half;
            }
        } else {
            int half = cycleLen / 2;
            diff[0] += cycleLen;
            if (half < n) {
                diff[half] -= cycleLen;
            }
        }
    }

    private void addLineAndCycle(long[] diff, int n, int lineLen, int cycleLen) {
        int half = cycleLen / 2;
        int add = (cycleLen % 2 == 1) ? 2 : 1;

        if (lineLen > 0) {
            diff[0] += 1;
            if (lineLen < n) {
                diff[lineLen] -= 1;
            }
        }

        for (int d = 1; d <= lineLen; d++) {
            int mn = d + 1;
            int mx = d + half;
            diff[mn - 1] += 2;
            diff[mx - 1] -= 2;
        }

        int mn2 = half + 1;
        int mx2 = half + lineLen;
        diff[mn2 - 1] += add;
        if (mx2 < n) {
            diff[mx2] -= add;
        }
    }

    private void addTwoLineAnswer(long[] diff, int n, int lineLen1, int lineLen2, int x, int y) {
        if (lineLen1 > lineLen2) {
            int temp = lineLen1;
            lineLen1 = lineLen2;
            lineLen2 = temp;
        }

        int plus = (x != y) ? 1 : 0;

        for (int i = 0; i < lineLen1; i++) {
            int mn = i + plus + 2;
            int mx = i + plus + 1 + lineLen2;
            diff[mn - 1]++;
            if (mx < n) {
                diff[mx]--;
            }
        }
    }
}
