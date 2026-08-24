package effibenchjmh.task3684.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    public boolean hasMatch(String str, String subStr) {
         int startIndex = subStr.indexOf("*");
         String prefix = subStr.substring(0,startIndex);
         String suffix = subStr.substring(startIndex+1);
         String str2 = str.substring(str.indexOf(prefix)+prefix.length());
         System.out.println(prefix);
         System.out.println(suffix);
         System.out.println(str2);
         if(str.contains(prefix) && str2.contains(suffix))
            return true;

         return false;
    }
}
