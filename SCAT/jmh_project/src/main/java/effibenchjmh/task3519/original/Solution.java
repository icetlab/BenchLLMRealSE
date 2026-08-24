package effibenchjmh.task3519.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    public int winningPlayerCount(int n, int[][] pick) {
        int count=0;
        HashMap<Integer,HashMap<Integer,Integer>> map=new HashMap<>();
        for(int arr[]:pick)
        {
            int i=arr[0];
            int j=arr[1];
            if(map.containsKey(i))
            {
               HashMap<Integer,Integer> h=map.get(i);
               if(h.containsKey(j))
               h.put(j,h.get(j)+1);
               else
               h.put(j,1);
               map.put(i,h);
            }
            else
            {
                HashMap<Integer,Integer> h=new HashMap<>();
                h.put(j,1);
                map.put(i,h);
            }
        }

        for(int i=0;i<n;i++)
        {
            if(!map.containsKey(i))
            continue;

            HashMap<Integer,Integer> h=map.get(i);
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
