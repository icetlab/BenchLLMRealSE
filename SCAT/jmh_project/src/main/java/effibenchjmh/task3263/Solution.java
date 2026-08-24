package effibenchjmh.task3263;

import java.util.*;
import java.util.stream.*;

public class Solution {
    public int minimumCost(int[] v) {
        int n = v.length;
        int s = v[0];
        Arrays.sort(v, 1, n);
        s = s + v[1] + v[2];
        return s;
    }
}
