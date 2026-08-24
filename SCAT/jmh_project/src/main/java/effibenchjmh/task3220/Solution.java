package effibenchjmh.task3220;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

public class Solution {
    public int countTestedDevices(int[] batteryPercentages) {
        int n = batteryPercentages.length;
        int testedDevices = 0;

        for (int i = 0; i < n; ++i) {
            if (batteryPercentages[i] > 0) {
                ++testedDevices;

                for (int j = i + 1; j < n; ++j) {
                    batteryPercentages[j] = Math.max(0, batteryPercentages[j] - 1);
                }
            }
        }

        return testedDevices;
    }
}
