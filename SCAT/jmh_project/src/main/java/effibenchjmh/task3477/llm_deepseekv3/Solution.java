package effibenchjmh.task3477.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int minOperations(int[] nums) {
        int f = 0;

        for (int v : nums) {
            if (((v ^ f) & 1) == 0) {
                f++;
            }
        }

        return f;
    }
}
