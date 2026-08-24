package effibenchjmh.task3199.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    public int distributeCandies(int n, int limit) {
        int count=0;
        for(int i=0; i<=n; i++){
            if(i>limit) continue;
            for(int j=0; j<=n; j++){
                if(j>limit) continue;
                for(int k=0; k<=n; k++){
                    if(k>limit) continue;

                    int sum=i+j+k;
                    if(sum==n) count++;
                }
            }
        }
        return count;
    }
}
