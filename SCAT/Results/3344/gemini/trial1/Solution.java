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

        int min_u_idx = -1, max_u_idx = -1, min_v_idx = -1, max_v_idx = -1;
        int min_u = Integer.MAX_VALUE, max_u = Integer.MIN_VALUE;
        int min_v = Integer.MAX_VALUE, max_v = Integer.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            int u = points[i][0] + points[i][1];
            int v = points[i][0] - points[i][1];

            if (u < min_u) {
                min_u = u;
                min_u_idx = i;
            }
            if (u > max_u) {
                max_u = u;
                max_u_idx = i;
            }
            if (v < min_v) {
                min_v = v;
                min_v_idx = i;
            }
            if (v > max_v) {
                max_v = v;
                max_v_idx = i;
            }
        }

        java.util.Set<Integer> candidates = new java.util.HashSet<>();
        candidates.add(min_u_idx);
        candidates.add(max_u_idx);
        candidates.add(min_v_idx);
        candidates.add(max_v_idx);

        int res = Integer.MAX_VALUE;
        for (int removed_idx : candidates) {
            int current_min_u = Integer.MAX_VALUE, current_max_u = Integer.MIN_VALUE;
            int current_min_v = Integer.MAX_VALUE, current_max_v = Integer.MIN_VALUE;

            for (int i = 0; i < n; i++) {
                if (i == removed_idx) continue;
                int u = points[i][0] + points[i][1];
                int v = points[i][0] - points[i][1];
                current_min_u = Math.min(current_min_u, u);
                current_max_u = Math.max(current_max_u, u);
                current_min_v = Math.min(current_min_v, v);
                current_max_v = Math.max(current_max_v, v);
            }
            
            int max_dist = Math.max(current_max_u - current_min_u, current_max_v - current_min_v);
            res = Math.min(res, max_dist);
        }
        
        return res;
    }
}