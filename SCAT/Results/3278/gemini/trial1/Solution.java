class Solution {
    public int numberOfPairs(int[][] points) {
        Arrays.sort(points, (p1, p2) -> p1[0] != p2[0] ? p1[0] - p2[0] : p2[1] - p1[1]);

        int n = points.length;
        int count = 0;

        for (int i = 0; i < n; i++) {
            int yi = points[i][1];
            int maxYIntermediate = Integer.MIN_VALUE;
            for (int j = i + 1; j < n; j++) {
                int yj = points[j][1];
                if (yj <= yi) {
                    if (yj > maxYIntermediate) {
                        count++;
                    }
                    maxYIntermediate = Math.max(maxYIntermediate, yj);
                }
            }
        }

        return count;
    }
}