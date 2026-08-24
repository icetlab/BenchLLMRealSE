package effibenchjmh.task3310;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public long[] countOfPairs(int n, int x, int y) {
        if (x > y) {
            return countOfPairs(n, y, x);
        }

        if (x + 1 == y) {
            return countOfPairs(n, x, x);
        }

        int line = n - (y - x) + 1;
        int ring = y - x + 1;
        long[] result = new long[n];
        for (int k = 1; k < n; k++) {
            if (x == y) {
                result[k - 1] = n - k;
            } else {
                // Case 1: In ring pairs
                if (k <= ring / 2) {
                    result[k - 1] += k * 2 == ring ? ring / 2 : ring;
                }

                // Case 2: House pairs that is on the line without going into the ring
                result[k - 1] += Math.max(0, k == 1 ? line - k - 1 : line - k);

                // Case 3: One house out of the ring, the other house in the ring
                if (k != 1) {
                    int maxInRingLen = Math.min(k - 1, ring / 2);

                    int leftMinInRingLen = Math.max(1, k - (x - 1));
                    if (leftMinInRingLen <= maxInRingLen) {
                        result[k - 1] += 2 * (maxInRingLen - leftMinInRingLen + 1);
                        // Special case, in ring len is 1, which is overlapped with case 2
                        if (leftMinInRingLen == 1) {
                            result[k - 1]--;
                        }
                        // Special case, in ring len is ring/2 and ring is even, in this case there will be one less pair
                        if (maxInRingLen * 2 == ring) {
                            result[k - 1]--;
                        }
                    }

                    int rightMinInRingLen = Math.max(1, k - (n - y));
                    if (rightMinInRingLen <= maxInRingLen) {
                        result[k - 1] += 2 * (maxInRingLen - rightMinInRingLen + 1);
                        if (rightMinInRingLen == 1) {
                            result[k - 1]--;
                        }
                        if (maxInRingLen * 2 == ring) {
                            result[k - 1]--;
                        }
                    }
                }
            }

            result[k - 1] *= 2;
        }

        return result;
    }
}
