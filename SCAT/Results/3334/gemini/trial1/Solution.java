class Solution {
    public int minimumBoxes(int[] a, int[] c) {
        int ans = 0;
        long s = 0;
        for (int val : a) {
            s += val;
        }
        Arrays.parallelSort(c);

        int i = c.length - 1;
        while (s > 0 && i >= 0) {
            s -= c[i--];
            ans++;
        }

        return ans;
    }
}