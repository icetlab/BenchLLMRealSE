package effibenchjmh.task3469.llm_deepseekr1;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public static int helper(int red,int blue){
        int blueN = (int)(Math.sqrt(blue));
        
        int n = (int)((Math.sqrt(1 + 4 * red) - 1) / 2);
        if (n*(n+1) > red) n--;
        while ((n+1)*(n+2) <= red) n++;
        if (red < 2) {
            n = 0;
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
