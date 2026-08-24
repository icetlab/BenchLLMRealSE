package effibenchjmh.task3278.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    public int numberOfPairs(int[][] points) {
        int n = points.length;
        int count = 0;
        Arrays.sort(points, (point1, point2) -> point2[1] - point1[1]);

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (isValidPair(points, points[i], points[j])) {
                    count++;
                }
                
                if (isValidPair(points, points[j], points[i])) {
                    count++;
                }
            }
        }

        return count;
    }

    private boolean isValidPair(int[][] points, int[] point1, int[] point2) {
        int upperLeftX = point1[0];
        int upperLeftY = point1[1];
        int lowerRightX = point2[0];
        int lowerRightY = point2[1];
        
        if (upperLeftX > lowerRightX || upperLeftY < lowerRightY) return false;
        for (int[] point : points) {
            int x = point[0];
            int y = point[1];
			 // for points[0] & points[1] count all such pairs where
             // no point is present in between them

            if (x >= upperLeftX && x <= lowerRightX && y <= upperLeftY && y >= lowerRightY && !Arrays.equals(point, point1) && !Arrays.equals(point, point2)) {
                return false;
            }
        }

        return true;
    }
}
