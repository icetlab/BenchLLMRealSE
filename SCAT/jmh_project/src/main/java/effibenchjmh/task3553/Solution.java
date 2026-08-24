package effibenchjmh.task3553;

import java.util.*;
import java.util.stream.*;

class Solution {
    public boolean checkTwoChessboards(String coordinate1, String coordinate2) {
        int a = (((int)coordinate1.charAt(0))%97)+1;
        int b = Character.getNumericValue(coordinate1.charAt(1));
        int c = (((int)coordinate2.charAt(0))%97)+1;
        int d = Character.getNumericValue(coordinate2.charAt(1));
        int col1=help(a,b);
        int col2=help(c,d);
        if(col1 == col2){
            return true;
        }
        return false;
    }
    public int help(int a,int b){
        if(a%2 == 0){
            if(b%2==0){
                return 1;
            }
            else{
                return 0;
            }
        }
        else{
            if(b%2==0){
                return 0;
            }
            else{
                return 1;
            }
        }
    } 
}
