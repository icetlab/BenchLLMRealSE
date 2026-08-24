package effibenchjmh.task3542;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    class Cell {
        int i;
        int j;
        int val;
        
        public Cell(int i, int j, int val) {
            this.i = i;
            this.j = j;
            this.val = val;
        }
    }
    
    public long maximumValueSum(int[][] board) {
        int rows = board.length;
        int cols = board[0].length;
        
        //Step 1: Prepare Cell List with at most 3 cells per row
        List<Cell> cellList = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            PriorityQueue<Cell> minHeap = new PriorityQueue<>((a, b) -> a.val - b.val);
            for (int j = 0; j < cols; j++) {
                minHeap.offer(new Cell(i, j, board[i][j]));
                if (minHeap.size() > 3) {
                    minHeap.poll();
                }
            }
            
            while (!minHeap.isEmpty()) {
                cellList.add(minHeap.poll());
            }
        }
        
        //Step 2: Prepare Cell List with at most 3 cells per column
        Collections.sort(cellList, (a, b) -> b.val - a.val);
        List<Cell> filteredList = new ArrayList<>();
        Map<Integer, Integer> columnCount = new HashMap<>();
        for (Cell cell : cellList) {
            int j = cell.j;
            if (columnCount.getOrDefault(j, 0) < 3) {
                filteredList.add(cell);
                columnCount.put(j, columnCount.getOrDefault(j, 0) + 1);
            }
        }
        
        //Step 3: Final ans
        long ans = Long.MIN_VALUE;
        for (int i = 0; i < Math.min(filteredList.size(), 15); i++) {
            for (int j = i+1; j < Math.min(filteredList.size(), 15); j++) {
                for (int k = j+1; k < Math.min(filteredList.size(), 15); k++) {
                    Cell cellI = filteredList.get(i);
                    Cell cellJ = filteredList.get(j);
                    Cell cellK = filteredList.get(k);
                    
                    if (cellI.i == cellJ.i || cellI.i == cellK.i || cellJ.i == cellK.i ||
                       cellI.j == cellJ.j || cellI.j == cellK.j || cellJ.j == cellK.j) {
                        continue;
                    }
                    
                    ans = Math.max(ans, 0L + cellI.val + cellJ.val + cellK.val);
                }
            }
        }
        
        return ans;
    }
}
