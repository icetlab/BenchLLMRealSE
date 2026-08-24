class Solution {
    public List<Integer> zigzagTraversal(int[][] grid) {
        //define input variables
        int cols = grid[0].length;
        int rows = grid.length;
        int inp_len = cols*rows;
        //define output variables
        int out_len = (inp_len-1)/2 + 1;
        Integer[] output = new Integer[out_len];
        int lastCol = cols - 1;

        for (int i = 0, r = 0, p = 0; i < out_len; i++, p += 2) {
            while (p >= cols) {
                p -= cols;
                r++;
            }
            int c = (r & 1) == 0 ? p : lastCol - p;
            output[i] = grid[r][c];
        }
        return List.of(output);
    }
}