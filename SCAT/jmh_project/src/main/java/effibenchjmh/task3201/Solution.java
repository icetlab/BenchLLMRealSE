package effibenchjmh.task3201;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
  public long distributeCandies(int n, int limit) {
    long count = 0;
    for (int a = Math.max(0, n - 2 * limit); a <= Math.min(limit, n); a++) {
      // range of b
      int b = Math.min(limit, n - a) - Math.max(0, n - a - limit) + 1;
      count += Math.max(0, b);
    }

    return count;
  }
}
