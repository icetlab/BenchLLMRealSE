import java.util.List;
import java.util.ArrayList;

class Solution {
    public List<Integer> zigzagTraversal(int[][] grid) {
        //define input variables
        int cols = grid[0].length;
        int rows = grid.length;
        int inp_len = cols*rows;
        //define output variables
        int out_len = (inp_len-1)/2 + 1;
        List<Integer> result = new ArrayList<>(out_len);

        for(int i = 0; i < out_len; i++){
            int r = (i*2)/cols;
            //int c = ((r%2)*(cols-1)) + (1-(r%2)*2)*((i*2)%cols);
            int c = (i*2)%cols;
            if(r%2==1){c=cols-1-c;}
            result.add(grid[r][c]); 
        }
        
        return result;
    }
}