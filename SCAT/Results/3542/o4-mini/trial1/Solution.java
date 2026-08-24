class Solution {
    public long maximumValueSum(int[][] board) {
        int m = board.length, n = board[0].length;
        ArrayList<ArrayList<int[]>> list = new ArrayList<>();
        for (int i = 0; i < m; ++i) {
            // find the top 3 values and their columns in row i by a single pass
            int v1 = Integer.MIN_VALUE, v2 = Integer.MIN_VALUE, v3 = Integer.MIN_VALUE;
            int c1 = -1, c2 = -1, c3 = -1;
            for (int j = 0; j < n; ++j) {
                int v = board[i][j];
                if (v > v1) {
                    v3 = v2; c3 = c2;
                    v2 = v1; c2 = c1;
                    v1 = v;  c1 = j;
                } else if (v > v2) {
                    v3 = v2; c3 = c2;
                    v2 = v;  c2 = j;
                } else if (v > v3) {
                    v3 = v;  c3 = j;
                }
            }
            // build a small list of up to three entries, already in descending order
            ArrayList<int[]> row = new ArrayList<>(3);
            row.add(new int[]{v1, c1});
            if (c2 != -1) row.add(new int[]{v2, c2});
            if (c3 != -1) row.add(new int[]{v3, c3});
            list.add(row);
        }
        long rs = Long.MIN_VALUE;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < 3; ++j){
                rs = Math.max(rs, calc(list, i, j));
            }
        }
        if (rs == Long.MIN_VALUE){
            return 0l + list.get(0).get(0)[0] + list.get(0).get(1)[0] + list.get(0).get(2)[0];
        }
        return rs;
    }
    public long calc(ArrayList < ArrayList < int[] >> list, int x, int y){
        int [][] arr = new int[][]{{-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}};
        for (int i = 0; i < list.size(); ++i){
            for (int j = 0; j < 3; ++j){
                if (i == x || list.get(x).get(y)[1] == list.get(i).get(j)[1]){
                    continue;
                }
                if (arr[0][0] == -1 || list.get(i).get(j)[0] >= list.get(arr[0][0]).get(arr[0][1])[0]){
                    arr[3][0] = arr[2][0];
                    arr[3][1] = arr[2][1];
                    arr[2][0] = arr[1][0];
                    arr[2][1] = arr[1][1];
                    arr[1][0] = arr[0][0];
                    arr[1][1] = arr[0][1];
                    arr[0][0] = i;
                    arr[0][1] = j;
                }
                else if (arr[1][0] == -1 || list.get(i).get(j)[0] >= list.get(arr[1][0]).get(arr[1][1])[0]){
                    arr[3][0] = arr[2][0];
                    arr[3][1] = arr[2][1];
                    arr[2][0] = arr[1][0];
                    arr[2][1] = arr[1][1];
                    arr[1][0] = i;
                    arr[1][1] = j;
                }
                else if (arr[2][0] == -1 || list.get(i).get(j)[0] >= list.get(arr[2][0]).get(arr[2][1])[0]){
                    arr[3][0] = arr[2][0];
                    arr[3][1] = arr[2][1];
                    arr[2][0] = i;
                    arr[2][1] = j;
                }
                else if (arr[3][0] == -1 || list.get(i).get(j)[0] >= list.get(arr[3][0]).get(arr[3][1])[0]){
                    arr[3][0] = i;
                    arr[3][1] = j;
                }
            }
        }
        if (max(list, arr) == Long.MIN_VALUE){
            return Long.MIN_VALUE;
        }
        return 0l + list.get(x).get(y)[0] + max(list, arr);
    }
    public long max(ArrayList < ArrayList < int[] >> list, int [][] arr){
        long rs = Long.MIN_VALUE;
        for (int i = 0; i < 4; ++i){
            for (int j = i + 1; j < 4; ++j){
                if (arr[i][0] == arr[j][0] || list.get(arr[i][0]).get(arr[i][1])[1] == list.get(arr[j][0]).get(arr[j][1])[1]){
                    continue;
                }
                rs = Math.max(rs, 0l + list.get(arr[i][0]).get(arr[i][1])[0] + list.get(arr[j][0]).get(arr[j][1])[0]);
            }
        }
        return rs;
    }
}
