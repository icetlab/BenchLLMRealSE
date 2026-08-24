package effibenchjmh.task3542.llm_gemini;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public long maximumValueSum(int[][] board) {
        int m = board.length, n = board[0].length;
        ArrayList < ArrayList < int[] >> list = new ArrayList < ArrayList < int[] >> ();
        for (int i = 0; i < m; ++i) {
            list.add(new ArrayList < int[] > ());
        }
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                list.get(i).add(new int[] {
                    board[i][j], j
                });
            }
        }
        for (int i = 0; i < m; ++i) {
            list.get(i).sort((arr1, arr2) -> arr2[0] - arr1[0]);
        }
        long rs = Long.MIN_VALUE;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < 3 && j < list.get(i).size(); ++j) {
                rs = Math.max(rs, calc(list, i, j));
            }
        }
        if (rs == Long.MIN_VALUE){
            return 0l + list.get(0).get(0)[0] + list.get(0).get(1)[0] + list.get(0).get(2)[0];
        }
        return rs;
    }
    public long calc(ArrayList < ArrayList < int[] >> list, int x, int y) {
        int[][] arr = new int[][] {
            {-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}
        };
        long[] arr_vals = new long[] {Long.MIN_VALUE, Long.MIN_VALUE, Long.MIN_VALUE, Long.MIN_VALUE};
        int excluded_col = list.get(x).get(y)[1];
        int m = list.size();

        for (int i = 0; i < m; ++i) {
            if (i == x) {
                continue;
            }
            ArrayList < int[] > row = list.get(i);
            for (int j = 0; j < 3 && j < row.size(); ++j) {
                int[] current = row.get(j);
                if (excluded_col == current[1]) {
                    continue;
                }
                long val = current[0];
                if (val > arr_vals[0]) {
                    arr_vals[3] = arr_vals[2]; arr[3][0] = arr[2][0]; arr[3][1] = arr[2][1];
                    arr_vals[2] = arr_vals[1]; arr[2][0] = arr[1][0]; arr[2][1] = arr[1][1];
                    arr_vals[1] = arr_vals[0]; arr[1][0] = arr[0][0]; arr[1][1] = arr[0][1];
                    arr_vals[0] = val; arr[0][0] = i; arr[0][1] = j;
                } else if (val > arr_vals[1]) {
                    arr_vals[3] = arr_vals[2]; arr[3][0] = arr[2][0]; arr[3][1] = arr[2][1];
                    arr_vals[2] = arr_vals[1]; arr[2][0] = arr[1][0]; arr[2][1] = arr[1][1];
                    arr_vals[1] = val; arr[1][0] = i; arr[1][1] = j;
                } else if (val > arr_vals[2]) {
                    arr_vals[3] = arr_vals[2]; arr[3][0] = arr[2][0]; arr[3][1] = arr[2][1];
                    arr_vals[2] = val; arr[2][0] = i; arr[2][1] = j;
                } else if (val > arr_vals[3]) {
                    arr_vals[3] = val; arr[3][0] = i; arr[3][1] = j;
                }
            }
        }

        long max_pair_sum = Long.MIN_VALUE;
        for (int i = 0; i < 4; ++i) {
            if (arr[i][0] == -1) continue;
            for (int j = i + 1; j < 4; ++j) {
                if (arr[j][0] == -1) continue;
                if (arr[i][0] != arr[j][0] && list.get(arr[i][0]).get(arr[i][1])[1] != list.get(arr[j][0]).get(arr[j][1])[1]) {
                    max_pair_sum = Math.max(max_pair_sum, arr_vals[i] + arr_vals[j]);
                }
            }
        }

        if (max_pair_sum == Long.MIN_VALUE) {
            return Long.MIN_VALUE;
        }
        return 0l + list.get(x).get(y)[0] + max_pair_sum;
    }

    public long max(ArrayList < ArrayList < int[] >> list, int[][] arr) {
        long rs = Long.MIN_VALUE;
        for (int i = 0; i < 4; ++i) {
            if (arr[i][0] == -1) continue;
            for (int j = i + 1; j < 4; ++j) {
                if (arr[j][0] == -1) continue;
                if (arr[i][0] == arr[j][0] || list.get(arr[i][0]).get(arr[i][1])[1] == list.get(arr[j][0]).get(arr[j][1])[1]) {
                    continue;
                }
                rs = Math.max(rs, 0l + list.get(arr[i][0]).get(arr[i][1])[0] + list.get(arr[j][0]).get(arr[j][1])[0]);
            }
        }
        return rs;
    }
}

