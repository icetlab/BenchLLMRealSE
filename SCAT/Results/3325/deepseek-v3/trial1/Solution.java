class Solution {
    record Rectangle(int x1, int y1, int x2, int y2) {}

    public long largestSquareArea(int[][] bottomLeft, int[][] topRight) {

        ArrayList<Rectangle> rectangles = new ArrayList<>();
        for (int i = 0; i < bottomLeft.length; i++) {
            rectangles.add(new Rectangle(
                bottomLeft[i][0],
                bottomLeft[i][1],
                topRight[i][0],
                topRight[i][1]));
        }

        long max = 0;

        for (int i = 0; i < rectangles.size(); i++) {
            for (int j = i + 1; j < rectangles.size(); j++) {
                long size = maxSquare(rectangles.get(i), rectangles.get(j));
                if (size > max) {
                    max = size;
                }
            }
        }

        return max * max;
    }

    long maxSquare(Rectangle r1, Rectangle r2) {
        // Find overlap directly without swapping
        int x1 = Math.max(r1.x1, r2.x1);
        int x2 = Math.min(r1.x2, r2.x2);
        int y1 = Math.max(r1.y1, r2.y1);
        int y2 = Math.min(r1.y2, r2.y2);

        int xDim = x2 - x1;
        int yDim = y2 - y1;

        if (xDim > 0 && yDim > 0) {
            return Math.min(xDim, yDim);
        }

        return 0;
    }
}