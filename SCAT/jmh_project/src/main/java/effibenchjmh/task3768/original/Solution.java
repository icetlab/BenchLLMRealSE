package effibenchjmh.task3768.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    public boolean hasSameDigits(String s) {
        boolean flag=false;
        int n=s.length();
        String newString="";

        for(int i=0;i<n-1;i++){
            int first=Integer.parseInt(String.valueOf(s.charAt(i)));
            int second=Integer.parseInt(String.valueOf(s.charAt(i+1)));
            int newNumber=(first+second)%10;
            newString+=newNumber;
            if(i>=(n-2)){
                if(newString.length()==2){
                   break;
                }else{
                    i=-1;
                    s=newString;
                    newString="";
                    n=s.length();
                }
            }
        }
        //System.out.print("NewString: "+newString);
        if(newString.charAt(0)==newString.charAt(1)){
            return true;
        }

        return false;
        
    }
}
