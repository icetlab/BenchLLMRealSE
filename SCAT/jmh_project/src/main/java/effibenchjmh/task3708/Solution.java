package effibenchjmh.task3708;

import java.util.*;
import java.util.stream.*;

class Solution {
    public List<Integer> zigzagTraversal(int[][] grid) {
        List<Integer> res = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < grid.length; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (index % 2 == 0)
                        res.add(grid[i][j]);
                    index++;
                }
            } else {
                for (int j = grid[i].length - 1; j >= 0; j--) {
                    if (index % 2 == 0)
                        res.add(grid[i][j]);
                    index++;
                }
            }
        }
        return res;
    }
}
