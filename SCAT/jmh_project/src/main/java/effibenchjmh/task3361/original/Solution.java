package effibenchjmh.task3361.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    public String findLatestTime(String s) {
                String[] sa=s.split(":");
        int min1=Integer.MAX_VALUE;
        int min2=Integer.MAX_VALUE;

        for(int i=0;i<sa.length;i++){
            String value=sa[i];
            if(i==0) {
                if(value.charAt(0)=='?' && value.charAt(1)=='?'){
                    min1=0;
                }else{
                        for (int number = 0; number < 10; number++) {
                        String s1=value.replace("?", String.valueOf(number));
                        int nb = Integer.parseInt(s1);
                        int diff = 11 - nb;
                        if(diff>=0)
                        min1 = Math.min(min1, diff);
                    }
                }    
                
            }else {
                 if(value.charAt(0)=='?' && value.charAt(1)=='?'){
                    min2=0;
                }else{
                for (int number = 0; number < 10; number++) {
                    String s2 = value.replace("?", String.valueOf(number));
                    int nb = Integer.parseInt(s2);
                    int diff = 59 - nb;
                    if(diff>=0)
                    min2 = Math.min(min2, diff);
                }
              }
            }

        }

        String s4= String.valueOf(11-min1);
        if(s4.length()==1)
        s4="0"+s4;
        String s5=String.valueOf(59-min2);
       if(s5.length()==1)
        s5="0"+s5;
        String ans=String.join(":",s4,s5);

        return ans;
    }
}
