package effibenchjmh.task3477.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    public int minOperations(int[] nums) {
        var f = 0;

        for (var v : nums) {
            if ((v + f) % 2 == 0) {
                f++;
            }
        }

        return f;
    }
}
