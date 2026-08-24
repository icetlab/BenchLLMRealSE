package effibenchjmh.task3245.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    public List<Integer> beautifulIndices(String s, String a, String b, int k) {
        
        List<Integer> aArray  =  construct(s,a);
        List<Integer> bArray =  construct(s,b);

        List<Integer> answer =  new ArrayList<>();
        int i=0;
        while(i <  aArray.size())
        {
            for(int j=0;j<  bArray.size();j++)
            {
                if(Math.abs(aArray.get(i) -  bArray.get(j)) <=k )
                {
                    answer.add(aArray.get(i));
                    break;
                }
            }
            i++;
        }
        return answer;
    }

   private List<Integer> construct(String s, String pattern) {
    String combined = pattern + "#" + s;
    int[] array = new int[combined.length()];
    
    int i = 1, len = 0;

    while (i < combined.length()) {
        if (combined.charAt(i) == combined.charAt(len)) {
            len++;
            array[i] = len;
            i++;
        } else {
            if (len != 0) {
                len = array[len - 1];
            } else {
                i++;
            }
        }
    }

    List<Integer> list = new ArrayList<>();
    for (i = pattern.length() + 1; i < combined.length(); i++) {
        if (array[i] == pattern.length()) {
            list.add(i - 2 * pattern.length());  // Corrected index calculation
        }
    }

    return list;
}

}
