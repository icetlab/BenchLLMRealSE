package effibenchjmh.task3220.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int countTestedDevices(int[] arr) {
        int tested = 0;
        for (int battery : arr) {
            if (battery - tested > 0) {
                tested++;
            }
        }
        return tested;
    }
}
