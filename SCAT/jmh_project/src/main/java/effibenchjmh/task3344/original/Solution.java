package effibenchjmh.task3344.original;

import java.util.*;
import java.util.stream.*;

class Solution {

    class Point {
        int i;
        int val;
        Point(int i, int val) {
            this.i = i;
            this.val = val;
        }
    }

    public int minimumDistance(int[][] points) {
        int n = points.length;

        HashMap<Integer, Integer> count1 = new HashMap<>();
        TreeSet<Integer> set1 = new TreeSet<>();
        HashMap<Integer, Integer> count2 = new HashMap<>();
        TreeSet<Integer> set2 = new TreeSet<>();

        int idx = 0;
        for (int[] p : points) {
            int a = p[0] + p[1];
            int b = p[0] - p[1];
            set1.add(a);
            set2.add(b);
            count1.put(a, count1.getOrDefault(a, 0) + 1);
            count2.put(b, count2.getOrDefault(b, 0) + 1);
        }

        int res = Integer.MAX_VALUE;
        for (int[] p : points) {
            int a = p[0] + p[1];
            int b = p[0] - p[1];

            count1.put(a, count1.getOrDefault(a, 0) - 1);
            count2.put(b, count2.getOrDefault(b, 0) - 1);
            if (count1.get(a) == 0) {
                set1.remove(a);
            }
            if (count2.get(b) == 0) {
                set2.remove(b);
            }

            res = Math.min(res, Math.max(set1.last() - set1.first(), set2.last() - set2.first()));

            set1.add(a);
            set2.add(b);
            count1.put(a, count1.getOrDefault(a, 0) + 1);
            count2.put(b, count2.getOrDefault(b, 0) + 1);
        }
        
        return res;
    }
}
