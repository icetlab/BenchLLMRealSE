import java.util.Arrays;

class Solution {
    public long maximumTotalSum(int[] maximumHeight) {
        Arrays.sort(maximumHeight);
        int n = maximumHeight.length;
        for (int i = n - 1; i > 0; i--) {
            if (maximumHeight[i] <= maximumHeight[i-1]) {
                maximumHeight[i-1] = maximumHeight[i] - 1;
            }
        }
        long total = 0;
        for (int height : maximumHeight) {
            if (height <= 0) return -1;
            total += height;
        }
        return total;
    }
}