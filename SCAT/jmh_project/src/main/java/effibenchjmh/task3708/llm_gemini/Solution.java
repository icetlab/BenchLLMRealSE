package effibenchjmh.task3708.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public List<Integer> zigzagTraversal(int[][] grid) {
        int cols = grid[0].length;
        int rows = grid.length;
        int inp_len = cols * rows;
        int out_len = (inp_len - 1) / 2 + 1;
        List<Integer> result = new java.util.ArrayList<>(out_len);

        for (int i = 0; i < out_len; i++) {
            int r = (i * 2) / cols;
            int c = (i * 2) % cols;
            if (r % 2 == 1) {
                c = cols - 1 - c;
            }
            result.add(grid[r][c]);
        }
        return result;
    }
}
