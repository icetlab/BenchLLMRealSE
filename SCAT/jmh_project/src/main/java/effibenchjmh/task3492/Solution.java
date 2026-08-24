package effibenchjmh.task3492;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int numberOfSubmatrices(char[][] grid) {
        int[] countX = new int[grid[0].length];
        int[] countY = new int[grid[0].length];
        int answer = 0;
        for(int i = 0; i < grid.length; i++) {
            int x = 0;
            int y = 0;
            for(int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == 'X') x++;
                if(grid[i][j] == 'Y') y++;
                countX[j] += x;
                countY[j] += y;
                if(countX[j] >= 1 && countX[j] == countY[j]) answer++;
            }
        }
        return answer;
    }
}
