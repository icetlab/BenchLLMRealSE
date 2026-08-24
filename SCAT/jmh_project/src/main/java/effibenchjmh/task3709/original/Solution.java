package effibenchjmh.task3709.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public boolean hasSpecialSubstring(String s, int k) {
        String str = "" + s.charAt(0);
        // if(s.length()>0 && k==1) {
        //     return true;
        // }
        for(int i=1; i<s.length(); i++) {
            if(s.charAt(i-1) == s.charAt(i)) {
                str+= s.charAt(i-1);
            } else {
                if(str.length() == k) {
                    return true;
                }
                str =""+ s.charAt(i);
            }
        }
        if(str.length() == k) {
            return true;
        }
        return false;
    }
}
