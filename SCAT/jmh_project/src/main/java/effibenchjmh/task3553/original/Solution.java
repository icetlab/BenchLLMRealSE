package effibenchjmh.task3553.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    public boolean checkTwoChessboards(String coordinate1, String coordinate2) {
        int col1 = coordinate1.charAt(0)-'a';
        int row1 = coordinate1.charAt(1)- '1';
        int color1 = (col1+row1)%2;

        int col2 = coordinate2.charAt(0)-'a';
        int row2 = coordinate2.charAt(1)- '1';
        int color2 = (col2+row2)%2;

        if (color1==color2){
            System.out.print("both have same color ");
            return true;

        }else{
            System.out.println("they have different colour");
            return false;
        }

    }
}
