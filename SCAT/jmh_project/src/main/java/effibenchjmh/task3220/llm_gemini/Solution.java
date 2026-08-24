package effibenchjmh.task3220.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int countTestedDevices(int[] arr) {
        int testedDevices = 0;
        for (int batteryPercentage : arr) {
            if (batteryPercentage > testedDevices) {
                testedDevices++;
            }
        }
        return testedDevices;
    }
}
