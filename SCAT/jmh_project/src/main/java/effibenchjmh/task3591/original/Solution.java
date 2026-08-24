package effibenchjmh.task3591.original;

import java.util.*;
import java.util.stream.*;

class Solution {
    public long calculateReverse(char start,char end,int[] previousCost)
    {
        long sum=0;
        int s= ((start-'a')+1)%26;
        int e = end-'a';
        while(true)
        {
            sum += previousCost[s];
            if(s==e)
            {
                break;
            }
            s = (s+1)%26;
        }

        return sum;
    }
    public long calculateStraight(char start,char end,int[] nextCost)
    {
        long sum=0;
        int s=start-'a';
        int e = end-'a';
        while(true)
        {
            sum+=nextCost[s];
            s=(s+1)%26;
            if(s==e)
            {
                break;
            }
        }

        return sum;
    }
    public long shiftDistance(String s, String t, int[] nextCost, int[] previousCost) {

        long mainSum = 0;

        for(int i=0;i<s.length();i++)
        {
            if(s.charAt(i)!=t.charAt(i))
            {
                long revSum = calculateReverse(t.charAt(i),s.charAt(i),previousCost);
                long straSum = calculateStraight(s.charAt(i),t.charAt(i),nextCost);
                mainSum+= Math.min(revSum,straSum);
            }
        }
        return mainSum;
    }
}
