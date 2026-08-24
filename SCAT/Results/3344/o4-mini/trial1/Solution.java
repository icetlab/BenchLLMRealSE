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
        if (n <= 1) return 0;
        int[] a = new int[n], b = new int[n];
        for (int i = 0; i < n; i++) {
            int x = points[i][0], y = points[i][1];
            a[i] = x + y;
            b[i] = x - y;
        }
        int[] aSorted = a.clone(), bSorted = b.clone();
        java.util.Arrays.sort(aSorted);
        java.util.Arrays.sort(bSorted);
        int minA = aSorted[0], maxA = aSorted[n-1];
        int minB = bSorted[0], maxB = bSorted[n-1];
        int secondMinA = aSorted[1], secondMaxA = aSorted[n-2];
        int secondMinB = bSorted[1], secondMaxB = bSorted[n-2];
        int countMinA = 1; while (countMinA < n && aSorted[countMinA] == minA) countMinA++;
        int countMaxA = 1; while (countMaxA < n && aSorted[n-1-countMaxA] == maxA) countMaxA++;
        int countMinB = 1; while (countMinB < n && bSorted[countMinB] == minB) countMinB++;
        int countMaxB = 1; while (countMaxB < n && bSorted[n-1-countMaxB] == maxB) countMaxB++;
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            int ra;
            if (a[i] == minA) ra = maxA - (countMinA >= 2 ? minA : secondMinA);
            else if (a[i] == maxA) ra = (countMaxA >= 2 ? maxA : secondMaxA) - minA;
            else ra = maxA - minA;
            int rb;
            if (b[i] == minB) rb = maxB - (countMinB >= 2 ? minB : secondMinB);
            else if (b[i] == maxB) rb = (countMaxB >= 2 ? maxB : secondMaxB) - minB;
            else rb = maxB - minB;
            res = Math.min(res, Math.max(ra, rb));
        }
        return res;
    }
}