class Solution {
    public static int helper(int red,int blue){
        int blueN = (int) Math.sqrt(blue);
        int n = (int) ((Math.sqrt(1 + 4L * red) - 1) / 2);
        if (blueN > n) {
            return 2 * n + 1;
        } else {
            return 2 * blueN;
        }

    }
    public int maxHeightOfTriangle(int red, int blue) {
        return Math.max(helper(red,blue),helper(blue,red));
    }
}