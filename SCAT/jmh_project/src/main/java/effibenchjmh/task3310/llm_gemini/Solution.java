package effibenchjmh.task3310.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

public class Solution {

    // Combines a line and a cycle and counts pair distances
    private void lineAndCycle(long[] res, long[] diff, int n, int lineLen, int cycleLen) {
        for (int d = 1; d <= lineLen; d++) {
            int mn = d + 1;
            int mx = d + (cycleLen / 2);
            if (mn - 1 < n) diff[mn - 1] += 2;
            if (mx - 1 < n) diff[mx - 1] -= 2;
        }

        int mn = cycleLen / 2 + 1;
        int mx = cycleLen / 2 + lineLen;

        int add = (cycleLen % 2 == 1 ? 2 : 1);
        if (mn - 1 < n) diff[mn - 1] += add;
        if (mx < n) diff[mx] -= add;

        long current_diff = 0;
        for (int i = 0; i < n; i++) {
            current_diff += diff[i];
            res[i] += current_diff;
        }

        // Account for distances of `1` from the line to the center
        for (int len = 1; len <= lineLen; len++) {
            if (len - 1 < n) res[len - 1]++;
        }
    }

    // Computes distances in a single line/tree
    private void lineAnswer(long[] res, int lineLen) {
        for (int len = 1, add = lineLen - 1; len < lineLen; len++, add--) {
            res[len - 1] += add;
        }
    }

    // Computes distances in a cycle
    private void cycleAnswer(long[] res, int cycleLen) {
        for (int len = 1; len < cycleLen / 2; len++) {
            res[len - 1] += cycleLen;
        }

        if (cycleLen % 2 == 1) {
            int len = cycleLen / 2;
            if (len > 0) res[len - 1] += cycleLen;
        } else {
            int len = cycleLen / 2;
            if (len > 0) res[len - 1] += (cycleLen / 2);
        }
    }

    // Computes distances when two lines are connected via a central node or path
    private void twoLineAnswer(long[] res, long[] diff, int n, int lineLen1, int lineLen2, int x, int y) {
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

            if (mn - 1 < n) diff[mn - 1]++;
            if (mx < n) diff[mx]--;
        }

        long current_diff = 0;
        for (int i = 0; i < n; i++) {
            current_diff += diff[i];
            res[i] += current_diff;
        }
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

        // Contributions from pairs within each of the 3 segments
        lineAnswer(res, lineLen1);
        lineAnswer(res, lineLen2);
        cycleAnswer(res, cycleLen);

        // Contributions from pairs across different segments
        long[] diff = new long[n];

        lineAndCycle(res, diff, n, lineLen1, cycleLen);

        java.util.Arrays.fill(diff, 0L);
        lineAndCycle(res, diff, n, lineLen2, cycleLen);

        java.util.Arrays.fill(diff, 0L);
        twoLineAnswer(res, diff, n, lineLen1, lineLen2, x, y);

        for (int i = 0; i < n; i++) {
            res[i] *= 2; // Multiply since counting unordered pairs
        }

        return res;
    }
}
