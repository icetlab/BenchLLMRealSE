package effibenchjmh.task3207.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    public int findMinimumOperations(String s1, String s2, String s3) {
        int count=0;
       while(!(s1.equals(s2)&&s2.equals(s3))){
        if (s1.isEmpty() || s2.isEmpty() || s3.isEmpty()) {
                return -1;
            }
            if(s1.length()>=s2.length() && s1.length()>=s3.length())
            s1=s1.substring(0,s1.length()-1);
            else if(s2.length()>=s1.length() && s2.length()>=s3.length())
            s2=s2.substring(0,s2.length()-1);
          else{
       s3=s3.substring(0,s3.length()-1);
        }
            
         count++;  

        
       }
        return count;
    }
}
