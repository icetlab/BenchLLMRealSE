
class Solution {

    int[][] dirs = {
        {-1, 1}, // topRight
        {1, 1}, // bottomRight
        {1, -1}, // bottomLeft
        {-1, -1} // topLeft
    };
    int res = 0;
    int[][][][][] memo;

    public int lenOfVDiagonal(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        memo = new int[m][n][2][4][2];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int t = 0; t < 2; t++) {
                    for (int d = 0; d < 4; d++) {
                        memo[i][j][t][d][0] = -1;
                        memo[i][j][t][d][1] = -1;
                    }
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    for (int k = 0; k < dirs.length; k++) {
                        int sub = dfs(grid, 2, i, j, k, false);
                        if (sub > res) res = sub;
                    }
                }
            }
        }
        return res;
    }

    private int dfs(int[][] grid, int target, int i, int j, int d, boolean turned) {
        int targetIdx = target == 2 ? 0 : 1;
        int turnedIdx = turned ? 1 : 0;
        if (memo[i][j][targetIdx][d][turnedIdx] != -1) {
            return memo[i][j][targetIdx][d][turnedIdx];
        }

        int[] dir = dirs[d];
        int newX = i + dir[0];
        int newY = j + dir[1];
        if (newX < 0 || newY < 0 || newX >= grid.length || newY >= grid[0].length || grid[newX][newY] != target) {
            memo[i][j][targetIdx][d][turnedIdx] = 1;
            return 1;
        }

        int nextTarget = target == 2 ? 0 : 2;
        int straight = dfs(grid, nextTarget, newX, newY, d, turned) + 1;
        int turn = 0;
        if (!turned) {
            turn = dfs(grid, nextTarget, newX, newY, (d + 1) % 4, true) + 1;
        }
        int best = straight > turn ? straight : turn;
        memo[i][j][targetIdx][d][turnedIdx] = best;
        return best;
    }
}