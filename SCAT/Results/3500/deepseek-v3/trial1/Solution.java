class Solution {
    public long minimumCost(int m, int n, int[] horizontalCut, int[] verticalCut) {
        // Sort ascending and iterate from the end to avoid boxing overhead.
        Arrays.sort(horizontalCut);
        Arrays.sort(verticalCut);

        int h = horizontalCut.length - 1;
        int v = verticalCut.length - 1;
        long cost = 0;
        long hp = 1, vp = 1;

        while (h >= 0 && v >= 0) {
            if (verticalCut[v] > horizontalCut[h]) {
                cost += (long) verticalCut[v] * hp;
                vp++;
                v--;
            } else {
                cost += (long) horizontalCut[h] * vp;
                hp++;
                h--;
            }
        }

        while (h >= 0) {
            cost += (long) horizontalCut[h] * vp;
            hp++;
            h--;
        }

        while (v >= 0) {
            cost += (long) verticalCut[v] * hp;
            vp++;
            v--;
        }

        return cost;
    }
}