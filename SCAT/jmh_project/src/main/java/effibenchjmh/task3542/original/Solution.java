package effibenchjmh.task3542.original;

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
            Collections.sort(list.get(i), new Comparator < int[] > () {
                public int compare(int[] arr1, int[] arr2) {
                    return arr2[0] - arr1[0];
                }
            });
        }
        long rs = Long.MIN_VALUE;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < 3; ++j){
                rs = Math.max(rs, calc(list, i, j));
            }
        }
        if (rs == Long.MIN_VALUE){
            return 0l + list.get(0).get(0)[0] + list.get(0).get(1)[0] + list.get(0).get(2)[0];
        }
        return rs;
    }
    public long calc(ArrayList < ArrayList < int[] >> list, int x, int y){
        int [][] arr = new int[][]{{-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}};
        for (int i = 0; i < list.size(); ++i){
            for (int j = 0; j < 3; ++j){
                if (i == x || list.get(x).get(y)[1] == list.get(i).get(j)[1]){
                    continue;
                }
                if (arr[0][0] == -1 || list.get(i).get(j)[0] >= list.get(arr[0][0]).get(arr[0][1])[0]){
                    arr[3][0] = arr[2][0];
                    arr[3][1] = arr[2][1];
                    arr[2][0] = arr[1][0];
                    arr[2][1] = arr[1][1];
                    arr[1][0] = arr[0][0];
                    arr[1][1] = arr[0][1];
                    arr[0][0] = i;
                    arr[0][1] = j;
                }
                else if (arr[1][0] == -1 || list.get(i).get(j)[0] >= list.get(arr[1][0]).get(arr[1][1])[0]){
                    arr[3][0] = arr[2][0];
                    arr[3][1] = arr[2][1];
                    arr[2][0] = arr[1][0];
                    arr[2][1] = arr[1][1];
                    arr[1][0] = i;
                    arr[1][1] = j;
                }
                else if (arr[2][0] == -1 || list.get(i).get(j)[0] >= list.get(arr[2][0]).get(arr[2][1])[0]){
                    arr[3][0] = arr[2][0];
                    arr[3][1] = arr[2][1];
                    arr[2][0] = i;
                    arr[2][1] = j;
                }
                else if (arr[3][0] == -1 || list.get(i).get(j)[0] >= list.get(arr[3][0]).get(arr[3][1])[0]){
                    arr[3][0] = i;
                    arr[3][1] = j;
                }
            }
        }
        if (max(list, arr) == Long.MIN_VALUE){
            return Long.MIN_VALUE;
        }
        return 0l + list.get(x).get(y)[0] + max(list, arr);
    }
    public long max(ArrayList < ArrayList < int[] >> list, int [][] arr){
        long rs = Long.MIN_VALUE;
        for (int i = 0; i < 4; ++i){
            for (int j = i + 1; j < 4; ++j){
                if (arr[i][0] == arr[j][0] || list.get(arr[i][0]).get(arr[i][1])[1] == list.get(arr[j][0]).get(arr[j][1])[1]){
                    continue;
                }
                rs = Math.max(rs, 0l + list.get(arr[i][0]).get(arr[i][1])[0] + list.get(arr[j][0]).get(arr[j][1])[0]);
            }
        }
        return rs;
    }
}

