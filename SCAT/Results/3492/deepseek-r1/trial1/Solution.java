class Solution {
    class Pair {
        int xCount;
        int yCount;

        public Pair(int xCount, int yCount) {
            this.xCount = xCount;
            this.yCount = yCount;
        }
    }

    public int numberOfSubmatrices(char[][] grid) {
        int n = grid[0].length;
        int[] xCount = new int[n];
        int[] yCount = new int[n];
        int count = 0;

        for (char[] row : grid) {
            int rowX = 0;
            int rowY = 0;
            for (int j = 0; j < n; j++) {
                char c = row[j];
                if (c == 'X') {
                    rowX++;
                } else if (c == 'Y') {
                    rowY++;
                }
                xCount[j] += rowX;
                yCount[j] += rowY;
                if (xCount[j] != 0 && xCount[j] == yCount[j]) {
                    count++;
                }
            }
        }

        /*
        int m = grid.length;
        int n = grid[0].length;
        List<List<Pair>> prefixSum = new ArrayList<>();
        
        //int[][] prefixSum = new int[m][n];
        int count = 0;

        // initializing our prefixSum array 
        for(int i=0; i<m; i++) {
            List<Pair> temp = new ArrayList<>();
            for(int j=0; j<n; j++) {
                if(grid[i][j] == 'X') {
                    temp.add(new Pair(1,0));
                }
                else if(grid[i][j] == 'Y') {
                    temp.add(new Pair(0,1));
                }
                else {
                    temp.add(new Pair(0,0));
                }

            }
            prefixSum.add(temp);
        }

        // prefixSum of each row
        for(int i=0; i<m; i++) {
            for(int j=1; j<n; j++) {
                Pair p1 = prefixSum.get(i).get(j-1);
                Pair p2 = prefixSum.get(i).get(j);
                p2.xCount += p1.xCount;
                p2.yCount += p1.yCount;
            }
        }

        // prefixSum of each col
        for(int j=0; j<n; j++) {
            for(int i=1; i<m; i++) {
                Pair p1 = prefixSum.get(i-1).get(j);
                Pair p2 = prefixSum.get(i).get(j);
                p2.xCount += p1.xCount;
                p2.yCount += p1.yCount;
            }
        }


        for(int i=0; i<m; i++) {
            for(int j=0; j<n; j++) {
                Pair p = prefixSum.get(i).get(j);
                if((p.xCount != 0) && (p.xCount == p.yCount)){
                    count++;
                }
            }
        }

        */
        return count;

    }
}