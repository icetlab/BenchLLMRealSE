package effibenchjmh.task3278.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int numberOfPairs(int[][] points) {
        int n = points.length;
        int count = 0;
        
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int[] p1 = points[i];
                int[] p2 = points[j];
                
                // Check if p1 is upper-left and p2 is lower-right
                if (p1[0] <= p2[0] && p1[1] >= p2[1]) {
                    if (isValidRectangle(points, p1, p2)) {
                        count++;
                    }
                }
                
                // Check if p2 is upper-left and p1 is lower-right
                if (p2[0] <= p1[0] && p2[1] >= p1[1]) {
                    if (isValidRectangle(points, p2, p1)) {
                        count++;
                    }
                }
            }
        }
        
        return count;
    }
    
    private boolean isValidRectangle(int[][] points, int[] upperLeft, int[] lowerRight) {
        int x1 = upperLeft[0];
        int y1 = upperLeft[1];
        int x2 = lowerRight[0];
        int y2 = lowerRight[1];
        
        for (int[] p : points) {
            if (p == upperLeft || p == lowerRight) continue;
            int x = p[0];
            int y = p[1];
            if (x >= x1 && x <= x2 && y <= y1 && y >= y2) {
                return false;
            }
        }
        return true;
    }
}
