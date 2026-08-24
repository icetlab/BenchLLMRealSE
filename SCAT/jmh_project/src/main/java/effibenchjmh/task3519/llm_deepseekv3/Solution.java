package effibenchjmh.task3519.llm_deepseekv3;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import effibenchjmh.common.Pair;

class Solution {
    public int winningPlayerCount(int n, int[][] pick) {
        int count=0;
        HashMap<Integer,HashMap<Integer,Integer>> map=new HashMap<>();
        for(int arr[]:pick)
        {
            int i=arr[0];
            int j=arr[1];
            HashMap<Integer,Integer> h=map.computeIfAbsent(i,k->new HashMap<>());
            h.put(j,h.getOrDefault(j,0)+1);
        }

        for(int i=0;i<n;i++)
        {
            HashMap<Integer,Integer> h=map.get(i);
            if(h==null)
            continue;

            for(int k:h.values())
            {
                if(k>=i+1)
                {
                count++;
                break;
                }
            }
        }

        return count;
    }
}
