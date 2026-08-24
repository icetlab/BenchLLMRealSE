package effibenchjmh.task3469.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    public static int helper(int red,int blue){
        int blueN = (int)(Math.sqrt(blue));
        
        int n = 1;

        while((n*(n+1))<=red){
            n++;

        }
        n--;
        if(red <2){
            n=0;
        }
        System.out.println(n);
        System.out.println(blueN);

        if(blueN>n){
            return (2*n)+1;
        }
        else if(blueN<n){
            return (2*blueN);
        }
        else{
            return (2*blueN);
        }

    }
    public int maxHeightOfTriangle(int red, int blue) {
        return Math.max(helper(red,blue),helper(blue,red));
    }
}
