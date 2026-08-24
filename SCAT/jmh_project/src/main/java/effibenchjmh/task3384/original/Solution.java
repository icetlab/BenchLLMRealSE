package effibenchjmh.task3384.original;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int minimumOperationsToMakeKPeriodic(String word, int k) {
        
        HashMap<String, Integer> mp = new HashMap<>();
        String maxStr = new String();
        Integer inmax = 0;
        for(int i =k; i<=word.length(); i+=k){
            String s = word.substring(i-k, i);
            if(mp.get(s) != null){
                mp.put(s,mp.get(s)+1);
                if(mp.get(s) > inmax){
                    maxStr = s;
                    inmax = mp.get(s);
                }
            }else {
                mp.put(s,1);
            }
        }
        if(inmax <= 1){
            return (word.length()/k) -1;
        }
        else{
            Integer ans = 0;
            for(Map.Entry<String, Integer> i: mp.entrySet()){
                // System.out.println(i.getKey());
                if(!i.getKey().equals(maxStr)){
                    ans+= i.getValue();
                }
            } 
            return ans;
        }
    }


}
