package effibenchjmh.task3468.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public String getEncryptedString(String s, int k) {
        int n=s.length();
        char[] result = new char[n];
        for(int i=0;i<n;i++){
            result[i]=s.charAt((i+k)%n);
        }
        return new String(result);
    }
}
