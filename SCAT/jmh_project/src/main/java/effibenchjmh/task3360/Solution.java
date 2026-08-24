package effibenchjmh.task3360;

import java.util.*;
import java.util.stream.*;

class Solution {
    public int minimumDeletions(String word, int k) {
        int a[]=new int[26];
        for(char c:word.toCharArray())
        {
            a[c-'a']++;
        }
        Arrays.sort(a);
        List<Integer> nm = new ArrayList<>();
        for(int i=25;i>=0;i--)
        {
            if(a[i] == 0)
            {
                break;
            }
            else
            {
                nm.add(a[i]);
            }
        }
        int ans = Integer.MAX_VALUE;
        for(int i=0;i<nm.size();i++)
        {
            int data=nm.get(i);
            int min=0;
            for(int j:nm)
            {
                if(j>(data + k))
                {
                    min += j-(data + k);
                }
                else if(j<data)
                {
                    min += j;
                }
            }
            ans=Math.min(ans, min);
            if(ans == 0)
            {
                break;
            }
        }
        return ans == Integer.MAX_VALUE ? 0 : ans;   
    }
}
