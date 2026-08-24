class Solution {
    private int maxHeight(int first, int second) {
        int height = 0;
        int row = 1;
        while (true) {
            if (row % 2 == 1) {
                if (first >= row) {
                    first -= row;
                    height++;
                } else {
                    break;
                }
            } else {
                if (second >= row) {
                    second -= row;
                    height++;
                } else {
                    break;
                }
            }
            row++;
        }
        return height;
    }

    public int maxHeightOfTriangle(int red, int blue) {
        return Math.max(maxHeight(red, blue), maxHeight(blue, red));
    }
}