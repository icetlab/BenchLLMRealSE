package effibenchjmh.task3210.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    public int beautifulSubstrings(String s, int k) {
        int n = s.length();
        int count = 0;
        String vowels = "aeiou";

        for(int i = 0; i < n; i++){
            int vowelCount = 0;
            int consonantCount = 0;
            
            for(int j = i; j < n; j++){
                char c = s.charAt(j);

                if(vowels.indexOf(c) != -1){
                    vowelCount++;
                } else{
                    consonantCount++;
                }
                if(vowelCount == consonantCount && ( vowelCount * consonantCount) % k == 0){
                        count++;
                    }
                }
            }
            return count;
        }   
}
