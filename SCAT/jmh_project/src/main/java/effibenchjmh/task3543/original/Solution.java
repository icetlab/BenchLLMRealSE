package effibenchjmh.task3543.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    public int countKConstraintSubstrings(String s, int k) {
        int c = 0;
        for (int i = 0; i<s.length(); i++){
            for (int j = i+1; j<=s.length();j++){
                if(cot(s.substring(i,j),k)){
                    c++;
                }
            }
        }
        return c;
    }
    public boolean cot(String s,int k){
        int z = 0;
        int o = 0;
        for(char c : s.toCharArray()){
            if (c == '0'){
                z++;
            }
            else{
                o++;
            }
        }
        if (o > k && z > k){
            return false;
        } 
        return true;
    }
}
