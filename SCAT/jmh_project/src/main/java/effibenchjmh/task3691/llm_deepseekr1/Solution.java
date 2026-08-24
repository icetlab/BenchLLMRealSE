package effibenchjmh.task3691.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int minimumOperations(int[][] grid) {
        int count=0;
		for (int j=0;j<grid[0].length ;j++ )
		{
			for (int i=0;i<grid.length ;i++ )
			{
				for (int k=i+1;k<grid.length ;k++ )
				{
					while(grid[i][j]>=grid[k][j])
					{
						int diff = grid[i][j] + 1 - grid[k][j];
					count += diff;
					grid[k][j] = grid[i][j] + 1;
						// count is updated in the replacement above
					}
				}
			}
		}
		return count;
    }
}
