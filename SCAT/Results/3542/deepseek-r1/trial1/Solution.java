class Solution {
    public long maximumValueSum(int[][] board) {
        int m = board.length;
        int n = board[0].length;

        long[][] topVal = new long[m][3];
        int[][] topCol = new int[m][3];

        for (int i = 0; i < m; ++i) {
            long v0 = Long.MIN_VALUE, v1 = Long.MIN_VALUE, v2 = Long.MIN_VALUE;
            int c0 = -1, c1 = -1, c2 = -1;
            for (int j = 0; j < n; ++j) {
                long v = board[i][j];
                if (v > v0) {
                    v2 = v1; c2 = c1;
                    v1 = v0; c1 = c0;
                    v0 = v; c0 = j;
                } else if (v > v1) {
                    v2 = v1; c2 = c1;
                    v1 = v; c1 = j;
                } else if (v > v2) {
                    v2 = v; c2 = j;
                }
            }
            topVal[i][0] = v0; topCol[i][0] = c0;
            topVal[i][1] = v1; topCol[i][1] = c1;
            topVal[i][2] = v2; topCol[i][2] = c2;
        }

        long ans = Long.MIN_VALUE;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < 3; ++j) {
                long cur = calc(topVal, topCol, i, j);
                if (cur != Long.MIN_VALUE) {
                    ans = Math.max(ans, cur);
                }
            }
        }
        if (ans == Long.MIN_VALUE) {
            return topVal[0][0] + topVal[0][1] + topVal[0][2];
        }
        return ans;
    }

    private long calc(long[][] topVal, int[][] topCol, int x, int y) {
        int m = topVal.length;
        long[] bestVal = new long[4];
        int[] bestRow = new int[4];
        int[] bestCol = new int[4];
        for (int k = 0; k < 4; ++k) {
            bestRow[k] = -1;
        }

        int firstCol = topCol[x][y];
        for (int i = 0; i < m; ++i) {
            if (i == x) {
                continue;
            }
            long[] rowVal = topVal[i];
            int[] rowCol = topCol[i];
            for (int j = 0; j < 3; ++j) {
                if (rowCol[j] == firstCol) {
                    continue;
                }
                long v = rowVal[j];
                int pos = 0;
                while (pos < 4 && bestRow[pos] != -1 && v < bestVal[pos]) {
                    ++pos;
                }
                if (pos < 4) {
                    for (int k = 3; k > pos; --k) {
                        bestRow[k] = bestRow[k - 1];
                        bestCol[k] = bestCol[k - 1];
                        bestVal[k] = bestVal[k - 1];
                    }
                    bestRow[pos] = i;
                    bestCol[pos] = rowCol[j];
                    bestVal[pos] = v;
                }
            }
        }

        long bestPair = Long.MIN_VALUE;
        for (int a = 0; a < 4; ++a) {
            if (bestRow[a] == -1) {
                continue;
            }
            for (int b = a + 1; b < 4; ++b) {
                if (bestRow[b] == -1) {
                    continue;
                }
                if (bestRow[a] != bestRow[b] && bestCol[a] != bestCol[b]) {
                    long sum = bestVal[a] + bestVal[b];
                    if (sum > bestPair) {
                        bestPair = sum;
                    }
                }
            }
        }

        if (bestPair == Long.MIN_VALUE) {
            return Long.MIN_VALUE;
        }
        return topVal[x][y] + bestPair;
    }
}
