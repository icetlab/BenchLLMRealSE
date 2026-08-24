import java.util.Arrays;

class Solution {
    public int minimumBoxes(int[] a, int[] c) {
        int ans = 0;
        int s = 0;
        for (int v : a) s += v;
        Arrays.sort(c);

        for (int i = c.length - 1; i >= 0 && s > 0; i--) {
            s -= c[i];
            ans++;
        }

        return ans;
    }
}